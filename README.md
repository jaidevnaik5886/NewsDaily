# 📰 News App (Jetpack Compose, Kotlin, Hilt)

This is a simple News App built with **Jetpack Compose** using the **MVVM architecture**. The app fetches top headlines from the [NewsAPI](https://newsapi.org/) and presents them in a scrollable list with detail screens. The structure follows clean architecture principles and demonstrates object-oriented modeling, dependency injection with Hilt, and proper error handling.

---

## 📱 Features

- Fetch top sports headlines from the US
- Modern UI using Jetpack Compose
- Navigation between news list and detail screens
- ViewModel + Repository + UseCase pattern
- Retrofit for networking
- Unified `ViewState` and `Result` sealed classes for API state management
- Robust error handling and retry logic
- Material 3 theming and dynamic color support
- String and color constants for better localization and theme control

---

## 🧱 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **MVVM Architecture**
- **Hilt (DI)**
- **Retrofit2 + Gson**
- **Coroutines / Flow**
- **Material 3**
- **Coil (for image loading)**

---

## 🛠️ Tools Used

**ChatGPT**

- **Code Scaffolding**: Used selectively to speed up repetitive boilerplate (e.g., sealed classes, DI modules, error handling templates), allowing more focus on architecture and logic.
- **Architecture Validation**: Acted as a sounding board for package structuring, dependency boundaries, and clean architecture alignment.
- **Design Discussion**: Assisted in evaluating theming consistency and composable structuring aligned with Material 3 design principles.


---

## ✅ Assignment Focus

- ✅ Object-oriented modeling
- ✅ Modular and clean architecture
- ✅ Kotlin & Compose usage
- ✅ Safe API handling with retries and error parsing
- ✅ Usage of constants and resource files
- ✅ Clean, maintainable code structure

---

## 🔑 License

This project is for educational/demo purposes only. NewsAPI's free tier is rate-limited and should not be used in production without proper key management.


