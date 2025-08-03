package com.example.newsdaily.network

import android.util.Log
import com.example.newsdaily.utils.isValidString
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

object RemoteSource {

    private const val DEFAULT_RETRY_COUNT = 3

    suspend fun <T : Any> safeApiCall(
        needRetry: Boolean = true,
        call: suspend () -> Response<T>,
    ): Result<T> {
        var count = 0
        var backOffTime = 2_000L
        while (count < DEFAULT_RETRY_COUNT) {
            try {
                val response = call.invoke()
                count = DEFAULT_RETRY_COUNT + 1
                if (response.isSuccessful) {
                    return if (response.body() != null) {
                        Result.Success(response.body()!!)
                    } else {
                        Result.SuccessWithNoContent
                    }
                }
                return handleResponseFailure(response)
            } catch (socketTimeOutException: SocketTimeoutException) {
                return if (isValidString(socketTimeOutException.message)) {
                    Result.Error(
                        ErrorBody(
                            "TimeOut error : ${socketTimeOutException.message}",
                            ERROR_CODE_TIMEOUT,
                            ""
                        )
                    )
                } else {
                    Result.Error(
                        ErrorBody(
                            DEFAULT_ERROR_MESSAGE_NO_INTERNET,
                            ERROR_CODE_TIMEOUT,
                            ""
                        )
                    )
                }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                if (needRetry) {
                    delay(backOffTime)
                    count++
                    backOffTime *= 2
                } else {
                    return if (isValidString(ioException.message)) {
                        Result.Error(ErrorBody("Parse error : ${ioException.message}", -1, ""))
                    } else {
                        Result.Error(
                            ErrorBody(
                                DEFAULT_ERROR_MESSAGE_NO_INTERNET,
                                ERROR_CODE_TIMEOUT,
                                ""
                            )
                        )
                    }
                }
            } catch (cancellationException: CancellationException) {
                cancellationException.printStackTrace()
                return if (isValidString(cancellationException.message)) {
                    Result.Error(
                        ErrorBody(
                            "Parse error : ${cancellationException.message}",
                            ERROR_CODE_CANCELLATION_JOB,
                            ""
                        )
                    )
                } else {
                    Result.Error(ErrorBody("", ERROR_CODE_CANCELLATION_JOB, ""))
                }

            } catch (syntaxException: JsonSyntaxException) {
                return if (isValidString(syntaxException.message)) {
                    Result.Error(ErrorBody("Parse error : ${syntaxException.message}", -1, ""))
                } else {
                    Result.Error(ErrorBody("Parse error : $DEFAULT_ERROR_MESSAGE", -1, ""))
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                return if (isValidString(exception.message)) {
                    Result.Error(ErrorBody("Parse error : ${exception.message}", -1, ""))
                } else {
                    // An exception was thrown when calling the API so we're converting this to an ErrorBody
                    Result.Error(ErrorBody(DEFAULT_ERROR_MESSAGE, -1, ""))
                }
            }
        }

        return Result.Error(ErrorBody(DEFAULT_ERROR_MESSAGE, -1, ""))
    }

    /**
     * Handles API response failures.
     *
     * This function processes the error response from a Retrofit API call and returns a [Result.Error]
     * containing an [ErrorBody] with relevant error information.
     *
     * @param response The Retrofit [Response] object representing the failed API response.
     * @return A [Result.Error] containing an [ErrorBody] with error details.
     */
    private fun <T : Any> handleResponseFailure(response: Response<T>): Result.Error =
        response.errorBody()?.let { errorBody ->
            val errorDetail = errorBody.string()
            // try getting first error detail
            try {
                Result.Error(buildErrorModel(response, errorDetail))
                // Keeping old error flow intact
            } catch (ignore: Exception) {
                Log.e("RemoteSource", "Error parsing error response", ignore)
                val responseCode = response.code()
                val message = when (responseCode) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                        "Authentication failed. Please check your credentials."
                    }

                    HttpURLConnection.HTTP_NOT_FOUND -> {
                        "The requested resource was not found."
                    }

                    in HttpURLConnection.HTTP_BAD_REQUEST until HttpURLConnection.HTTP_INTERNAL_ERROR + 100 -> {
                        "Unable to process your request. Please try again later."
                    }

                    else -> {
                        DEFAULT_ERROR_MESSAGE
                    }
                }
                Result.Error(ErrorBody(message, responseCode, errorDetail))
            }
        } ?: Result.Error(ErrorBody(DEFAULT_ERROR_MESSAGE, -1, ""))

    /**
     * Handle response error, default common error view converted, override it to change implementation
     *
     * @param response raw retrofit response
     * @param errorDetail error string from error body
     */
    private fun <T : Any> buildErrorModel(response: Response<T>, errorDetail: String): ErrorBody {

        if (response.code() == 401 || response.code() == 403) {
            return ErrorBody("", response.code(), "")
        }

        if (errorDetail.contains("message")) {
            return Gson().fromJson(errorDetail, ErrorBody::class.java)
                .copy(code = response.code(), rawMessage = errorDetail)
        }

        if (!errorDetail.contains("message")) {
            throw IllegalArgumentException()
        }

        // parse the error body from the response, copy the object apply the response code
        return Gson().fromJson(errorDetail, ErrorBody::class.java)
            .copy(code = response.code(), rawMessage = errorDetail)
    }
}