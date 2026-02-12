# Roam Android SDK Integration Guide

A complete guide to integrating the **Roam Android SDK** into your application — from setup to permissions, tracking, services, and real-time location updates.

Git repo link:  https://github.com/roam-ai/roam-android

---

## 📦 Installation

### **Option A — Manual (.aar, Unreleased)**

1. Download the latest `.aar` from **GitHub Releases** or provided by Echo.
2. Create a `/libs` folder in your app module and place the `.aar` inside.
3. Add the dependency in **app/build.gradle**:

```
dependencies {
    implementation files("libs/roam.aar")
    implementation "com.google.android.gms:play-services-location:21.3.0"
    implementation "com.squareup.retrofit2:retrofit:2.11.0"
    implementation "com.squareup.retrofit2:converter-gson:2.11.0"
    implementation "com.google.android.gms:play-services-ads-identifier:18.2.0"
}

```

---

### **Option B — Gradle (Recommended, Released Version)**

Add to **settings.gradle**:

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

```

Add to **app/build.gradle**:

```
dependencies {
    implementation "io.github.roam-sdk:roam-android:0.2.4"
}

```

---

## 🚀 Initialization

Initialize Roam inside your **Application** class.

### **Kotlin**

```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Batch sync only
        Roam.initialize(this, "YOUR_SECRET_KEY")

        // or - Batch sync + live updates via receiver
        val receiver = LocationReceiver()
        Roam.initialize(this, "YOUR_SECRET_KEY", receiver)

        // Enable batch sync
        Roam.setConfig(true, RoamBatchPublish.Builder().enableAll().build())
    }
}

```

### **Java**

```java
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Batch sync only
        Roam.initialize(this, "YOUR_SECRET_KEY");

        // or - Batch sync + live updates via receiver
        LocationReceiver receiver = new LocationReceiver();
        Roam.initialize(this, "YOUR_SECRET_KEY", receiver);

        Roam.setConfig(true, new RoamBatchPublish.Builder().enableAll().build());
    }
}

```

---

## 🔐 Permissions

Roam supports **built-in** permission handling or **manual runtime** permission flows.

---

## 📲 SDK In-Built Permission Helpers

### **Phone State**

**Manifest**

```xml
<uses-permission android:name="android.permission.READ_PHONE_STATE" />

```

**Kotlin**

```kotlin
if (!Roam.checkPhoneStatePermission()) {
    Roam.requestPhoneStatePermission(this)
}

```

---

### **Location Permissions**

**Manifest**

```xml
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

```

**Kotlin**

```kotlin
if (!Roam.checkLocationPermission()) {
    Roam.requestLocationPermission(this)
} else if (!Roam.checkBackgroundLocationPermission()) {
    Roam.requestBackgroundLocationPermission(this)
} else if (!Roam.checkLocationServices()) {
    Roam.requestLocationServices(this)
} else if (!Roam.checkPostNotificationPermission()) {
    Roam.requestPostNotificationPermission(this)
}

```

---

## 🧩 Handling In-Built Permission Results

### **Kotlin**

```kotlin
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
        Roam.REQUEST_CODE_LOCATION_PERMISSION -> if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED)
            Log.d("Permissions", "Foreground location granted")

        Roam.REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION -> if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED)
            Log.d("Permissions", "Background location granted")

        Roam.REQUEST_CODE_POST_NOTIFICATION -> if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED)
            Log.d("Permissions", "Post Notifications granted")
    }
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == Roam.REQUEST_CODE_LOCATION_ENABLED) {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("Permissions", "Location services enabled")
        }
    }
}

```

### **Java**

```java
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                       @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
        case Roam.REQUEST_CODE_LOCATION_PERMISSION:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Log.d("Permissions", "Foreground location granted");
            break;

        case Roam.REQUEST_CODE_BACKGROUND_LOCATION_PERMISSION:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Log.d("Permissions", "Background location granted");
            break;

        case Roam.REQUEST_CODE_POST_NOTIFICATION:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Log.d("Permissions", "Post Notifications granted");
            break;
    }
}

```

---

## 🔧 Manual Runtime Permissions

**Kotlin**

```kotlin
// Request Fine Location permission
if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
    != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        2001
    )
}

// Request Background Location permission (Android 10+)
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
        2002
    )
}

// Request Phone State permission
if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
    != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.READ_PHONE_STATE),
        2005
    )
}

// Ensure GPS is enabled
val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 2003)
}

// Request Notification permission (Android 13+)
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            2004
        )
    }
}

```

**Java**

```java
// Request Fine Location permission
if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(
            this,
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
            2001
    );
}

// Request Background Location permission (Android 10+)
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(
            this,
            new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
            2002
    );
}

// Request Phone State permission
if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(
            this,
            new String[]{Manifest.permission.READ_PHONE_STATE},
            2005
    );
}

// Ensure GPS is enabled
LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 2003);
}

// Request Notification permission (Android 13+)
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
    ) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.POST_NOTIFICATIONS},
                2004
        );
    }
}

```

---

### 🧩 Handling Manual Runtime Permissions Results in Activity

**Kotlin**

```kotlin
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
        2001 -> if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED)
            Log.d("Permissions", "Foreground location granted")
        2002 -> if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED)
            Log.d("Permissions", "Background location granted")
        2004 -> if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED)
            Log.d("Permissions", "Post Notifications granted")
    }
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == 2003) {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("Permissions", "Location services enabled")
        }
    }
}
```

**Java**

```java
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                       @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
        case 2001:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions", "Foreground location granted");
            }
            break;
        case 2002:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions", "Background location granted");
            }
            break;
        case 2004:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions", "Post Notifications granted");
            }
            break;
    }
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 2003) {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("Permissions", "Location services enabled");
        }
    }
}

```

---

## 🔋 Foreground Service (Required for Background Tracking)

### **SDK Built-In Notification**

```kotlin
Roam.setForegroundNotification(
    true,
    "Tracking Active",
    "Tap to return to app",
    R.drawable.ic_launcher,
    "com.example.MainActivity"
)

```

### **Manual Example (Kotlin)**

```kotlin
class RoamForegroundLocationService : Service() {
    companion object {
        fun start(context: Context) {
            context.startForegroundService(Intent(context, RoamForegroundLocationService::class.java))
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())
        return START_STICKY
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "roam_service")
            .setContentTitle("Location Tracking")
            .setContentText("Running in background")
            .setSmallIcon(R.drawable.ic_launcher)
            .build()
    }
}

```

---

## ▶️ Start / ⏹ Stop Tracking

### **Kotlin**

```kotlin
Roam.startTracking(object : TrackingCallback {
    override fun onSuccess(message: String?) {}
    override fun onError(error: RoamError?) {}
})

Roam.stopTracking(object : TrackingCallback {
    override fun onSuccess(message: String?) {}
    override fun onError(error: RoamError?) {}
})

```

### **Java**

```java
Roam.startTracking(new TrackingCallback() {
    @Override
    public void onSuccess(String message) {}

    @Override
    public void onError(RoamError error) {}
});

```

---

## 📡 Listen for Location Updates

### **Kotlin**

```kotlin
class RoamLocationReceiver : RoamReceiver() {
    override fun onLocationUpdated(context: Context, roamLocation: RoamLocation) {
        Log.d("RoamLocationReceiver", "Location: $roamLocation")
    }

    override fun onError(context: Context?, roamError: RoamError?) {
        Log.e("RoamLocationReceiver", "Error: $roamError")
    }
}

```

### **Manifest**

```xml
<receiver android:name=".RoamLocationReceiver"
          android:enabled="true"
          android:exported="false">
    <intent-filter>
        <action android:name="com.roam.android.RECEIVED"/>
    </intent-filter>
</receiver>

```
