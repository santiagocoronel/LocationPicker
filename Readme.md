# Location Picker Challenge

This project is part of a technical challenge that consists of developing an Android application to visualize a list of cities, filter them, mark them as favorites, and view their location on a map. The project fully adheres to the principles of **Clean Architecture** and **SOLID**.

---

## Technologies and Stack

- **Language**: Kotlin 2.0.21
- **Architecture**: Clean Architecture (UI, Presentation, Domain, Data)
- **Jetpack Compose**: Material 3
- **Navigation**: Navigation Compose
- **Maps**: Google Maps with Compose
- **Persistence**: Room
- **Networking**: Retrofit + kotlinx.serialization
- **Dependency Injection**: Koin
- **Testing**:
  - Unit tests: JUnit 5 + MockK
  - StateFlow tests: Turbine (CashApp)
  - UI tests: Jetpack Compose Testing

---

## Project Structure

```bash
LocationPicker/
├── app/
├── core/
│   ├── core-ui
│   ├── core-network
│   ├── core-local
│   ├── core-domain
│   └── core-utils
├── feature-locationpicker/
│   ├── feature-locationpicker-ui
│   ├── feature-locationpicker-presentation
│   ├── feature-locationpicker-domain
│   ├── feature-locationpicker-data
│   ├── feature-locationpicker-local
│   └── feature-locationpicker-remote
```

---

Features
•	Display cities loaded from a remote or local JSON source.
•	Filter by text.
•	Favorite toggle filter.
•	Mark/unmark cities as favorites.
•	Detail screen with city information.
•	View selected city on a map.
•	Support for portrait and landscape orientations:
•	In portrait: city list and navigation to map screen.
•	In landscape: city list and map displayed side by side.

⸻

Testing

All layers of the architecture are tested, except the UI layer. The project follows the Android Testing Mantra:

Given → When → Then — each test is clearly divided into preparation, action, and validation.

Unit Tests

The following components are covered:
•	Use cases (Domain layer)
•	Repositories (Data layer)
•	ViewModels (Presentation layer)

Tools used:
•	JUnit 5
•	MockK
•	kotlinx.coroutines.test
•	Turbine (for testing StateFlow in ViewModels)

UI Tests (Jetpack Compose)

Covered interactions:
•	Filter and toggle switches
•	Text input

Tools used:
•	androidx.compose.ui.test
•	createComposeRule

⸻

Data Synchronization
•	Initially attempts to fetch city data from a remote URL.
•	If the fetch fails, falls back to a local JSON file stored in the assets directory.
•	Sync is only retriggered if the number of cities differs from what’s stored locally.

⸻

Navigation

The application supports navigation between:
•	Main screen with city list
•	Detail screen
•	Map screen

In portrait mode, standard navigation is used.
In landscape mode, both list and map are displayed simultaneously.

⸻

Technical Decisions
•	Animations were not included in favor of focusing on core functionality.
•	Reading the local JSON file is handled via Android’s assets system, which justifies the use of Context in the Data module.
•	Orientation-based layout rendering is implemented using BoxWithConstraints.

⸻

Requirements
•	Android Studio Iguana or newer
•	Gradle 8.11.1 or higher
•	Kotlin 2.0.21
•	Minimum SDK: API 28

⸻

Installation
1.	Clone the repository
2.	Open with Android Studio
3.	Make sure you provide a valid Google Maps API key
4.	Run the app on a device or emulator

⸻

License

This project was developed for educational and technical evaluation purposes.