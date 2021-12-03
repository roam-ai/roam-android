<p align="center">
  <a href="https://roam.ai" target="_blank" align="left">
    <img src="https://github.com/geosparks/roam-flutter/blob/master/logo.png?raw=true" width="180">
  </a>
  <br />
</p>

# Official Roam Android SDK
This is the official [Roam](https://roam.ai) Android SDK developed and maintained by Roam B.V

Note: Before you get started [signup to our dashboard](https://roam.ai) to get your API Keys.

## Featured Apps

<a href="https://hugoapp.com"><img src="https://i.imgur.com/xRqznN9.png" width="100"></a>
<a href="https://handy.la"><img src="https://i.imgur.com/EDGcldE.png" width="100"></a>
<a href="https://pickerexpress.com"><img src="https://media.giphy.com/avatars/Picker_ec/lNLRCBxHo8BJ.png" width="100"></a>



## Quickstart

The Roam Android SDK makes it quick and easy to build a location tracker for your Android app. We provide powerful and customizable tracking modes and features.

### **Requirements**

To use the Roam SDK, the following things are required:

- [x] Get yourself a free [Roam Account](https://playground.geospark.co/). No credit card required.
- [x] Create a project and add an Android app to the project.
- [x] You need the `PUBLISHABLE_KEY` in your project settings which you’ll need to initialize the SDK.
- [x] Now you’re ready to integrate the SDK into your Android application.
- [x] The Roam Android SDK requires Android Studio 2.0 or later and is compatible with apps targeting Android SDK Version 16 or above.

### Android Studio Setup

To use the Android SDK in a project, add the SDK as a build dependency and sync the project.

1. Go to Android Studio > New Project > Minimum SDK

2. Select API 16: Android 4.1.0 (Jelly Bean) or higher and create a project

### Gradle Installation

Add the following to the build script `{repositories {}}` section of the `build.gradle` (Project)file

```java
mavenCentral()
```


Install the SDK to your project via `Gradle` in Android Studio, add the maven below in your project `build.gradle` file.

```java
repositories {
    maven {
        url 'https://com-roam-android.s3.amazonaws.com/'
    }
}
```

add the dependencies below in your `app build.gradle` file.

```
dependencies {
    implementation 'com.roam.sdk:roam-android:0.0.8'
}
```

Then sync Gradle.

### Manual Installation

[Download](https://github.com/roam-ai/roam-android/releases/download/0.0.8/Roam.zip) and unzip Roam SDK

1. Open Android Studio and add the SDK `Roam.aar` as a module using File > New > New Module > Import .JAR/.AAR Package.
2. Once Gradle is finished click File > Project Structure again.
3. Click on the Dependencies tab > click App > click the “+” icon in the top left in Declared Dependencies section > select Module Dependency > click on Roam-release > press Ok and wait for Gradle to sync again and include the dependencies separately and sync your project.
4. Wait for Gradle to sync again and include the dependencies separately and sync your project.

```java
dependencies {
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    implementation 'com.squareup.retrofit2:retrofit:2.6.2'
    implementation 'com.squareup.retrofit2:converter-gson:2.1.0'
    implementation 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.4'
    implementation 'org.eclipse.paho:org.eclipse.paho.android.service:1.1.1'
}
```

### Initialize SDK

Before initializing the SDK, the below must be imported.

```java
import com.roam.sdk.Roam;
```

After import, add the below code under the Application class `onCreate()` method. The SDK must be initialised before calling any of the other SDK methods.

```java
Roam.initialize(this, "YOUR-SDK-KEY-GOES-HERE");
```

### Creating Users

Once the SDK is initialized, we need to *create* or *get a user* to start the tracking and use other methods. Every user created will have a unique Roam identifier which will be used later to login and access developer APIs. We can call it as Roam userId.

```java
Roam.createUser("YOUR-USER-DESCRIPTION-GOES-HERE", new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                // Access Roam user data below
                // roamUser.getUserId();
                // roamUser.getDescription();
                // roamUser.getEventListenerStatus();
                // roamUser.getLocationListenerStatus();
                // roamUser.getLocationEvents();
                // roamUser.getGeofenceEvents();
                // roamUser.getMovingGeofenceEvents();
                // roamUser.getTripsEvents();
            }

            @Override
            public void onFailure(RoamError roamError) {
                // Access error code & message below
                // roamError.getCode();
                // roamError.getMessage();
            }
        });
```

The option *user description* can be used to update your user information such as name, address or add an existing user ID. Make sure the information is encrypted if you are planning to save personal user information like email or phone number.

You can always set or update user descriptions later using the below code.

```java
Roam.setDescription("SET-USER-DESCRIPTION-HERE");
```

### Get User

If you already have a Roam userID which you would like to reuse instead of creating a new user, use the below to get user session.

```java
Roam.getUser("YOUR-ROAM-USER-ID", new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
                // Access Roam user data below
                // roamUser.getUserId();
                // roamUser.getDescription();
                // roamUser.getEventListenerStatus();
                // roamUser.getLocationListenerStatus();
                // roamUser.getLocationEvents();
                // roamUser.getGeofenceEvents();
                // roamUser.getMovingGeofenceEvents();
                // roamUser.getTripsEvents();
            }

            @Override
            public void onFailure(RoamError roamError) {
                // Access error code & message below
                // roamError.getCode();
                // roamError.getMessage();
            }
        });
```

### Request Permission

 To request the location for devices running both below/above Android 10.

```java
if (!Roam.checkLocationServices()) {
       Roam.requestLocationServices(this);
} else if (!Roam.checkLocationPermission()) {
       Roam.requestLocationPermission(this);
} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Roam.checkBackgroundLocationPermission()) {
       Roam.requestBackgroundLocationPermission(this);
}
```

### SDK Configurations

#### Accuracy Engine

For enabling accuracy engine for Passive, Active, and Balanced tracking.

```java
Roam.enableAccuracyEngine();
```

For Custom tracking mores, you can pass the desired accuracy values in integers ranging from 25-150m.

```java
Roam.enableAccuracyEngine(50);
```
To disable accuracy engine

```java
Roam.disableAccuracyEngine();
```

#### Offline Location Tracking

To modify the offline location tracking configuration, which will enabled by default. 

```java
Roam.offlineLocationTracking(true);
```

#### Allow Mock Location Tracking

To allow mock location tracking during development, use the below code. This will be disabled by default. 

```java
Roam.allowMockLocation(true);
```

### Location Tracking

#### Start Tracking

Use the tracking modes while you use the startTracking method `Roam.startTracking`

#### **Tracking Modes**

Roam has three default tracking modes along with a custom version. They differ based on the frequency of location updates and battery consumption. The higher the frequency, the higher is the battery consumption. You must use [foreground service](https://developer.android.com/about/versions/oreo/background-location-limits) for continuous tracking.

| **Mode** | **Battery usage** | **Updates every** | **Optimised for/advised for** |
| -------- | ----------------- | ----------------- | ----------------------------- |
| Active   | 6% - 12%          | 25 ~ 250 meters   | Ride Hailing / Sharing        |
| Balanced | 3% - 6%           | 50 ~ 500 meters   | On Demand Services            |
| Passive  | 0% - 1%           | 100 ~ 1000 meters | Social Apps                   |

```java
// active tracking
Roam.startTracking(RoamTrackingMode.ACTIVE);
// balanced tracking
Roam.startTracking(RoamTrackingMode.BALANCED);
// passive tracking
Roam.startTracking(RoamTrackingMode.PASSIVE);
```

#### Custom Tracking Modes

The SDK also provides a custom tracking mode that allows you to customize and build your own tracking modes.

| **Type**          | **Unit** | **Unit Range** |
| ----------------- | -------- | -------------- |
| Distance Interval | Meters   | 1m ~ 2500m     |
| Time Interval     | Seconds  | 10s ~ 10800s   |

**Distance between location updates example code:**

```java
// Define a custom tracking method with desired distance interval, stop duration and accuracy
RoamTrackingMode trackingMode = new RoamTrackingMode.Builder(<DISTANCE-FILTER-IN-METERS>, <STOP-INTERVAL-IN-SECONDS>)
                .setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH)
                .build();
// Start the tracking with the above created custom tracking method
Roam.startTracking(trackingMode);
```

**Time between location updates example code:**

```java
// Define a custom tracking method with desired time interval and accuracy
RoamTrackingMode trackingMode = new RoamTrackingMode.Builder(<TIME-INTERVAL-IN-SECONDS>)
                .setDesiredAccuracy(RoamTrackingMode.DesiredAccuracy.HIGH)
                .build();
// Start the tracking with the above created custom tracking method
Roam.startTracking(trackingMode);
```

You may see a delay if the user's device is in low power mode or has connectivity issues.

#### Stop Tracking

To stop the tracking use the below method.

```java
Roam.stopTracking();
```

### **Publish Messages**

It will publish location data and these data will be sent to Roam servers for further processing and data will be saved in our database servers.

```java
RoamPublish locationData = new RoamPublish.Builder().build();
Roam.publishAndSave(locationData);
```

To stop publishing locations.

```java
Roam.stopPublishing();
```

### Subscribe Messages

Now that you have enabled the location listener, use the below method to subscribe to your own or other user's location updates and events.

#### Subscribe

```java
Roam.subscribe(TYPE, "ROAM-USER-ID");
```

#### UnSubscribe

```java
Roam.unSubscribe(TYPE, "USER-ID");
```

| **Type**                | **Description**                                              |
| ----------------------- | ------------------------------------------------------------ |
| Roam.Subscribe.EVENTS   | Subscribe to your own events.                                |
| Roam.Subscribe.LOCATION | Subscribe to your own location (or) other user's location updates. |
| Roam.Subscribe.BOTH     | Subscribe to your own events and location (or) other user's location updates. |

### Listeners

Now that the location tracking is set up, you can subscribe to locations and events and use the data locally on your device or send it directly to your own backend server.

To do that, you need to set the location and event listener to `true` using the below method. By default the status will set to `false` and needs to be set to `true` in order to stream the location and events updates to the same device or other devices.

```java
Roam.toggleListener(true, true, new RoamCallback() {
            @Override
            public void onSuccess(RoamUser roamUser) {
	        // Access Roam user data below
                // roamUser.getUserId();
                // roamUser.getDescription();
                // roamUser.getEventListenerStatus();
                // roamUser.getLocationListenerStatus();
                // roamUser.getLocationEvents();
                // roamUser.getGeofenceEvents();
                // roamUser.getMovingGeofenceEvents();
                // roamUser.getTripsEvents();
            }

            @Override
            public void onFailure(RoamError roamError) {
	        // Access error code & message below
                // roamError.getCode();
                // roamError.getMessage();
            }
        });
```

Once the listener toggles are set to true, to listen to location updates and events, create a class that extends GeoSparkReceiver. Then register the receiver by adding a receiver element to the application element in your manifest.

```java
<application>
        ...
     <receiver android:name=".LocationReceiver"
                    android:enabled="true"
                    android:exported="false">
         <intent-filter>
         <action android:name="com.roam.android.RECEIVED"/>
         </intent-filter>
     </receiver>
         ...
</application>
```

Then add the code to the receiver.

```java
public class LocationReceiver extends RoamReceiver {

    @Override
    public void onLocationUpdated(Context context, RoamLocation roamLocation) {
        super.onLocationUpdated(context, roamLocation);
        // receive own location updates here
	// do something with location data using location
	// roamLocation.getLocation().getLatitude() 
	// roamLocation.getLocation().getLongitude());
    }
    
    @Override
    public void onLocationReceived(Context context, RoamLocationReceived roamLocationReceived) {
        // receive other user's location updates here
	// do something with location
    }

    @Override
    public void onEventReceived(Context context, RoamEvent roamEvent) {
	//access event data here
    }

    @Override
    public void onError(Context context, RoamError roamError) {
    	// receive error message here
    	// roamError.getMessage());
    }

}
```

## Documentation

See the full documentation [here](https://docs.roam.ai/android).

## Example
See a Android example app in `Example/`.
To run the example app, clone this repository, add your sdk "YOUR-SDK-KEY" key in `MainActivity.java`, and run the app.

## Need Help?
If you have any problems or issues over our SDK, feel free to create a github issue or submit a request on [Roam Help](https://geosparkai.atlassian.net/servicedesk/customer/portal/2).

