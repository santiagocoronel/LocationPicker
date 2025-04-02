# Location Picker Challenge

Este proyecto es parte de un challenge técnico que consiste en desarrollar una aplicación Android para visualizar una lista de ciudades, filtrarlas, marcarlas como favoritas y ver su ubicación en un mapa. El proyecto respeta en su máxima expresión los principios de **Clean Architecture** y **SOLID**.

---

## 🧠 Tecnologías y stack utilizado

- **Lenguaje**: Kotlin 2.0.21
- **Arquitectura**: Clean Architecture (UI, Presentation, Domain, Data)
- **Jetpack Compose**: Material 3
- **Navigation**: Navigation Compose
- **Mapas**: Google Maps con Compose
- **Persistencia**: Room
- **Red**: Retrofit + kotlinx.serialization
- **Inyección de dependencias**: Koin
- **Testing**:
    - Unit tests: JUnit 5 + MockK
    - StateFlow tests: Turbine (CashApp)
    - UI tests: Jetpack Compose Testing

---

## 📁 Estructura del proyecto

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

✨ Features
	•	Visualización de ciudades obtenidas de un JSON remoto o local.
	•	Filtrado por texto.
	•	Filtro de favoritos (switch toggle).
	•	Marcado de ciudades como favoritas.
	•	Pantalla de detalle con más información.
	•	Visualización de la ciudad seleccionada en un mapa.
	•	Soporte para portrait y landscape:
	•	En portrait: lista de ciudades y navegación a mapa.
	•	En landscape: lista y mapa visibles en pantalla dividida.

⸻

🧪 Testing

Se testean todas las capas de la arquitectura excepto la UI, siguiendo el enfoque del Android Testing Mantra:

Given → When → Then: cada test se estructura dividiendo claramente su preparación, acción y validación.

Se utilizaron las siguientes herramientas:

✅ Unit Tests
	•	Se testean:
	•	UseCases (Domain)
	•	Repositories (Data)
	•	ViewModels (Presentation)
	•	Herramientas:
	•	JUnit 5
	•	MockK
	•	kotlinx.coroutines.test
	•	app.cash.turbine (para testear StateFlow de los ViewModels)

✅ UI Tests (Jetpack Compose)
	•	Se testea:
	•	Interacción con filtros y switches
	•	Entrada de texto
	•	Herramientas:
	•	androidx.compose.ui.test
	•	createComposeRule

⸻

🔄 Sincronización de datos
	•	La primera vez se intenta sincronizar con la URL remota.
	•	Si falla, se utiliza el JSON local precargado en assets.
	•	Solo se vuelve a sincronizar si la cantidad de ciudades difiere con respecto a la base local.

⸻

🧭 Navegación
	•	Se navega entre:
	•	Pantalla principal con lista
	•	Pantalla de detalle
	•	Pantalla de mapa
	•	En portrait: navegación clásica
	•	En landscape: ambas secciones conviven

⸻

🧩 Decisiones técnicas destacadas
	•	El proyecto no incluye animaciones para priorizar la funcionalidad (decisión documentada).
	•	La lectura del JSON local se realiza desde assets, lo cual justifica el uso limitado de Context en un módulo de Data.
	•	La visualización condicional (portrait vs landscape) se maneja con BoxWithConstraints.

⸻

⚙️ Requisitos
	•	Android Studio Iguana o superior
	•	Gradle 8.11.1+
	•	Kotlin 2.0.21
	•	API 28 o superior

⸻

🧳 Instalación
	1.	Clonar el repositorio
	2.	Abrir con Android Studio
	3.	Asegurarse de tener Google Maps API Key
	4.	Ejecutar con un emulador o dispositivo físico

⸻

📄 Licencia

Este proyecto fue desarrollado con fines educativos y de evaluación técnica.