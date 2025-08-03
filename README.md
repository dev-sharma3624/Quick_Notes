# 📝 QuickNotes – A Modern Jetpack Compose Notes App with Firebase

> QuickNotes is a modern Android notes application built entirely with Jetpack Compose. It combines the power of Firebase Authentication and Cloud Firestore to provide secure, user-specific, real-time note storage. Along with that, it uses a local Room database for offline-first support, so you can access your notes even when there’s no internet. Whether you’re a student, developer, or professional who needs to jot down ideas quickly, QuickNotes gives you a smooth, cloud-synced, offline-ready note-taking experience.


---

## ✨ Key Features

-   🔐 **Secure Login** — Google Sign-In via Firebase Authentication.
-   📂 **Per-User Notes** — Each user has their own dedicated collection in Firestore.
-   ⚡ **Real-Time Updates** — Notes sync instantly and automatically between devices.
-   💾 **Offline Support** — Notes are stored locally in a Room Database for access without an internet connection.
-   🛠️ **MVVM Architecture** — A clean, scalable, and maintainable codebase.
-   🎨 **Jetpack Compose UI** — A beautiful and modern UI built with Material 3 design principles.

---

## 🛠 Tech Stack

-   **Language:** Kotlin
-   **UI Framework:** Jetpack Compose
-   **Authentication:** Firebase Authentication
-   **Cloud Database:** Firebase Firestore
-   **Offline Storage:** Room Database
-   **Dependency Injection:** Koin
-   **Architecture:** MVVM (Model-View-ViewModel)

---

## 🚀 How to Run QuickNotes Locally

Follow these steps to get the project up and running on your local machine.

#### 1️⃣ **Clone the Repository**

```bash
git clone [https://github.com/](https://github.com/)<your-username>/QuickNotes.git
cd QuickNotes
```

#### 2️⃣ **Create a Firebase Project**

-   Go to the [Firebase Console](https://console.firebase.google.com/).
-   Click **Add Project**, enter a name for your project, and click **Continue**.

#### 3️⃣ **Register Your Android App**

-   In the Firebase Console, navigate to your project and click **Add app** → **Android**.
-   Enter your app’s package name (you can find this in `app/build.gradle` under `applicationId`).
-   Add your **SHA‑1 fingerprint** to connect Google Sign-In. You can get it by running:
    ```bash
    ./gradlew signingReport
    ```

#### 4️⃣ **Download & Add `google-services.json`**

-   After registering the app, click **Download google-services.json**.
-   Place the downloaded file in the `app/` directory of your project (`app/google-services.json`).

#### 5️⃣ **Enable Firebase Authentication**

-   In the Firebase Console, go to **Authentication** → **Sign-in method**.
-   Enable the **Google** provider.

#### 6️⃣ **Enable Cloud Firestore**

-   In the Firebase Console, go to **Firestore Database** → **Create Database**.
-   Select **Start in Test Mode** for development purposes.
-   Choose a server location that is nearest to you.

#### 7️⃣ **Add Firestore Security Rules**

-   Navigate to the **Rules** tab in your Firestore database and paste the following to ensure users can only access their own notes:
    ```javascript
    rules_version = '2';
    service cloud.firestore {
      match /databases/{database}/documents {
        match /users/{userId}/notes/{noteId} {
          allow read, write: if request.auth != null && request.auth.uid == userId;
        }
      }
    }
    ```

#### 8️⃣ **Add Firebase Dependencies**

-   Ensure the following dependencies are present in your Gradle files.

-   **`project/build.gradle`**
    ```gradle
    buildscript {
        dependencies {
            classpath("com.google.gms:google-services:4.4.2")
        }
    }
    ```

-   **`app/build.gradle`**
    ```gradle
    plugins {
        id("com.google.gms.google-services")
    }

    dependencies {
        // Firebase
        implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
        implementation("com.google.firebase:firebase-firestore-ktx:25.0.0")

        // Room
        implementation("androidx.room:room-runtime:2.6.1")
        implementation("androidx.room:room-ktx:2.6.1")
        kapt("androidx.room:room-compiler:2.6.1")
    }
    ```

#### 9️⃣ **Sync & Run**

-   Open the project in Android Studio.
-   Let Gradle sync the dependencies.
-   Run the app on an emulator or a physical device.

---

## 📌 How it Works

1.  When a user signs in with their Google account, **Firebase Authentication** verifies their identity and provides a unique user ID (`uid`).
2.  All notes are stored in **Cloud Firestore** under a path that includes this `uid`, ensuring strict user data isolation:
    ```
    users/{uid}/notes/{noteId}
    ```
3.  Real-time updates are achieved using **snapshot listeners**, which automatically sync data between the app and the cloud.
4.  For offline access, notes are also cached locally in the **Room Database**.
5.  On sign-out, the local database is cleared, and the real-time listeners are detached to secure the user's data.

---

## 📜 Conclusion

QuickNotes serves as a robust template for building modern Android applications. It demonstrates a powerful combination of:

-   Real-time cloud synchronization.
-   An offline-first architecture.
-   A clean and scalable MVVM design.
-   A fully declarative UI with Jetpack Compose.

You can easily extend this project with additional features like:

-   Note searching & filtering
-   Categories & labels
-   Rich text formatting (bold, italics, etc.)

With Firebase as the backend, QuickNotes can scale effortlessly while keeping user data secure and isolated.
