# Exercise App

Welcome to the Exercise App! This Android application helps users track their exercises and monitor their progress over time. It is built using Kotlin and Jetpack Compose, following modern Android development practices.

## Features

- Track various exercises with duration and type.
- Save exercise records to a local database (Room).
- View exercise history and progress.
- Clean and responsive UI built with Jetpack Compose.

## Technologies Used

- **Kotlin**: Programming language for Android development.
- **Jetpack Compose**: Modern UI toolkit for building native UIs.
- **Room Database**: For local data persistence.
- **MVVM Architecture**: Ensures a clean separation of concerns and scalability.
- **Coroutines**: For managing background tasks efficiently.

## Prerequisites

To build and run this app, you need:

- Android Studio Flamingo or later.
- Android SDK version 26 or higher.
- Kotlin version 1.6 or later.
- A device or emulator running Android 8.0 (API level 26) or higher.

## Getting Started

1. Clone this repository:

   ```bash
   git clone https://github.com/WeissShaharIL/exercise-app.git
   ```

2. Open the project in Android Studio.

3. Sync the Gradle project to download dependencies.

4. Run the app on an emulator or connected device.

## Project Structure

```
.
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   ├── com.example.exerciseapp
│   │   │   │   │   ├── model    # Data models
│   │   │   │   │   ├── view     # UI components
│   │   │   │   │   ├── viewmodel # ViewModel classes
│   │   │   ├── res
│   │   │   │   ├── layout       # XML layout files
│   │   │   │   ├── values       # App themes, strings, and styles
│   ├── build.gradle  # Module-level Gradle file
├── build.gradle      # Project-level Gradle file
└── README.md         # Project documentation
```

## Contributing

Contributions are welcome! If you'd like to contribute:

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/your-feature-name`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to your forked repository (`git push origin feature/your-feature-name`).
5. Open a Pull Request on the main repository.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any inquiries or feedback, please contact Shahar Weiss at [weiss.shahar.il@gmail.com](mailto:weiss.shahar.il@gmail.com).

---

Thank you for checking out the Exercise App! Feel free to explore, use, and improve it.
