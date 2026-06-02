# 📱 MediTrack Android

![Kotlin](https://img.shields.io/badge/Kotlin-100%25-7F52FF?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-4285F4?logo=jetpackcompose&logoColor=white)
![Android](https://img.shields.io/badge/Android-Native-3DDC84?logo=android&logoColor=white)
![MinSdk](https://img.shields.io/badge/minSdk-30-brightgreen)
![Version](https://img.shields.io/badge/Version-0.0.1-orange)
![License](https://img.shields.io/badge/License-MIT-yellow)

Native Android client for the MediTrack medication reminder platform. Built with Kotlin and Jetpack Compose, featuring exact alarms via AlarmManager, background sync via WorkManager, full-screen intent notifications, and JWT-authenticated communication with the [MediTrack REST API](https://github.com/LuisAlvarezMtz/meditrack-api).

---

## 🚀 Features

- **JWT Authentication** — Secure login with token persistence via DataStore
- **Role-based Experience** — Separate flows for Patients and Caregivers
- **Medication Management** — Full CRUD for medications and schedules
- **Exact Alarms** — Precise reminder delivery using AlarmManager with exact alarm permissions
- **Alarm Queue** — Manages multiple overlapping alarms reliably
- **Full Screen Intent** — Reminders appear as full-screen alerts even on locked screens
- **Foreground Alarm Service** — Keeps alarms alive even when the app is closed
- **Background Sync** — WorkManager + RetryWorker keeps local data in sync with the backend
- **Boot Recovery** — BootReceiver reschedules all alarms after device restart
- **History View** — Log of past medication reminders and actions

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Networking | Retrofit 2 + OkHttp (logging interceptor) |
| JSON | Gson Converter |
| Authentication | JWT + OkHttp Interceptor + DataStore |
| Local Storage | DataStore Preferences + Room |
| Alarms | AlarmManager (exact alarms) |
| Foreground Service | AlarmForegroundService + AlarmSoundService |
| Background Tasks | WorkManager + SyncWorker + RetryWorker |
| Notifications | Full Screen Intent + NotificationManager |
| Architecture | MVVM (ViewModel + LiveData + Compose) |
| Build System | Gradle (KTS) + Version Catalog |

---

## 📁 Project Structure

```
app/src/main/java/com/example/meditrackservice/
├── alarm/
│   ├── ActionReceiver          # Handles notification action buttons
│   ├── AlarmActivity           # Full-screen alarm UI
│   ├── AlarmForegroundService  # Keeps alarm alive when app is closed
│   ├── AlarmQueue              # Manages multiple scheduled alarms
│   ├── AlarmReceiver           # BroadcastReceiver triggered by AlarmManager
│   ├── AlarmScheduler          # Schedules exact alarms
│   ├── AlarmScreen.kt          # Compose screen shown on alarm trigger
│   ├── AlarmSoundService       # Plays alarm sound as foreground service
│   ├── BootReceiver            # Reschedules alarms after device reboot
│   └── OmitirAlarmaWorker      # Worker to dismiss/skip an alarm
├── data/
│   ├── api/
│   │   ├── ApiService          # Retrofit interface (all endpoints)
│   │   ├── AuthInterceptor     # Attaches JWT to every request
│   │   └── RetrofitClient      # Retrofit + OkHttp setup
│   ├── local/                  # Room database and DAOs
│   └── model/                  # Data models / DTOs
├── sync/
│   ├── RetryScheduler          # Schedules retry attempts
│   ├── RetryWorker             # WorkManager worker for failed requests
│   ├── SyncScheduler           # Schedules periodic background sync
│   └── SyncWorker              # WorkManager worker for data sync
└── ui/
    ├── historial/
    │   ├── HistorialActivity
    │   ├── HistorialScreen.kt
    │   └── HistorialViewModel.kt
    ├── login/
    │   ├── LoginActivity
    │   ├── LoginScreen.kt
    │   ├── LoginViewModel.kt
    │   └── LoginViewModelFactory
    ├── main/
    │   ├── AlarmaViewModel.kt
    │   ├── AlarmaViewModelFactory
    │   ├── MainActivity
    │   └── MainScreen.kt
    ├── splash/
    │   └── SplashActivity.kt
    └── theme/                  # App theme, colors, typography
```

---

## ⚙️ Configuration

The API base URL is defined in `RetrofitClient`:

```kotlin
object RetrofitClient {
    private const val BASE_URL_LOCAL  = "http://192.168.1.113:8080/"
    private const val BASE_URL_RENDER = "https://meditrackwebappback.onrender.com/"

    const val BASE_URL = BASE_URL_RENDER  // Switch to BASE_URL_LOCAL for local dev
}
```

### Android Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
<uses-permission android:name="android.permission.USE_EXACT_ALARM" />
<uses-permission android:name="android.permission.USE_FULL_SCREEN_INTENT" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_SPECIAL_USE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />
```

---

## 🏃 Running Locally

### Prerequisites

- Android Studio Hedgehog or later
- JDK 11+
- minSdk **30** / targetSdk **36**
- A running instance of the [MediTrack API](https://github.com/LuisAlvarezMtz/meditrack-api)

### Steps

```bash
# Clone the repository
git clone https://github.com/LuisAlvarezMtz/meditrack-android.git

# Open in Android Studio
# File → Open → select the project folder

# For local development, switch BASE_URL in RetrofitClient:
# const val BASE_URL = BASE_URL_LOCAL

# Run → Run 'app' or press Shift+F10
```

> ⚠️ For exact alarms to work on Android 12+, the user must grant the `SCHEDULE_EXACT_ALARM` permission manually in device settings (Settings → Apps → Special app access → Alarms & reminders).

---

## 🔔 Alarm & Notification System

Reminders are scheduled using **AlarmManager** with exact timing for precise delivery.

When a reminder fires:
1. `AlarmReceiver` (BroadcastReceiver) catches the alarm
2. `AlarmForegroundService` starts to keep the process alive
3. `AlarmSoundService` plays the alarm sound
4. A **Full Screen Intent** launches `AlarmActivity` on the locked screen
5. User can dismiss or snooze via `ActionReceiver`
6. `BootReceiver` reschedules all pending alarms after device reboot

**Background sync** is handled separately by `SyncWorker` and `RetryWorker` via WorkManager.

---

## 🔐 Authentication Flow

```
1. SplashActivity checks for stored JWT in DataStore
2. If valid  → navigate to MainActivity
3. If missing → navigate to LoginActivity
4. Login with phone + password → receive JWT from API
5. Token stored in DataStore via AuthInterceptor
6. All requests automatically include Authorization: Bearer <token>
```

---

## 📦 Release

| Version | Date | Notes |
|---|---|---|
| [0.0.1](https://github.com/LuisAlvarezMtz/meditrack-android/releases/tag/0.0.1) | Apr 2026 | Initial release |

---

## 🔗 Related Repositories

- [`meditrack-api`](https://github.com/LuisAlvarezMtz/meditrack-api) — Spring Boot REST API backend
- [`meditrack-web`](https://github.com/LuisAlvarezMtz/meditrack-web) — Vanilla JS web client

---

## 👤 Author

**Luis Angel Alvarez Martinez**
📧 [angel.alvarez20@hotmail.com](mailto:angel.alvarez20@hotmail.com)

---

## 📄 License

This project is licensed under the MIT License.
