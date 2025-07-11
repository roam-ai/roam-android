## 0.0.1

* First version of new Roam Android SDK

## 0.0.2
Fixed

- Fixed the Location receiver callbacks


## 0.0.3

- Added support for total elevation gain to trip summary along with elevation gain, distance and duration for location date in trip summary.

## 0.0.4

Modified:
- #11 Allow meta-data support for updating location ie. updateCurrentLocation method
Removed:
- #9 Combine startTracking and startSelfTracking methods

## 0.0.5

Modified:
Make startTrip independent by combining it with startTracking and createTrip methods
Added:
`metadata` support for users and trips are added

## 0.0.6

Fixed:
Location receiver when location was updated without `metadata`

## 0.0.7

Added:
Support to listen to location updates of user from different projects which are within same account.

## 0.0.8

Added:
Support to listen the location permission status in receiver class when location permission is changed.

## 0.0.9

Added:
- Foreground service notification in SDK.
- Elevation gain support for offline trips.

Modified:
- Removed user id validation for offline trips.

## 0.0.10

Modified:
Removed user id validation for offline trips in createTrip method.

## 0.0.11

Added:
- Initialize validation in each method.

Modified:
- Removed location permission status update.

## 0.0.12

Fixed:
- The Roam SDK installation support for android 12.

## 0.0.13

Fixed:
- The foreground notification method support for android 12.

## 0.0.14

Added:
- Battery status and network status field along with location update data in receiver class.
- New feature to unsubscribe more than one user's location.

Fixed:
- Restrcited invalid location update.

## 0.0.15

Fixed:
- Elevation gain logical error.

## 0.0.16

Fixed:
- Stationary location update issue.

## 0.0.17

Modified:
- Connection configuration for security and performance.

## 0.1.0

Modified:
- Updated new trip v2 methods. Refer Migration guide for more details.

## 0.0.18

Fixed:
- optimised location update by removing noise locations update when device gets idle.

## 0.0.19

Modified:
- made RoamPublish param as optional in updateCurrentLocation method. 

## 0.0.20

Fixed:
- time based tracking issue. 

## 0.0.21

Added:
- Batch location update feature.
- Location count in offline trip sync api and trip control api.

Modified:
- Trip error code response. 

## 0.0.22

Added:
- Connectivity change listener method.

## 0.0.23

Added:
- Batch location update feature for trip.

Fixed:
- location listener and connectivity change listener when device gets reboot. 

## 0.0.24

Fixed:
- Location drift issue. 
- Reciever intent unsubscribe issue. 

## 0.1.1

Fixed:
- Quick trip response.

## 0.0.25

Added:
- Unsubscribe trip method for multiple trips. 
- Timestamp field in trip status listener.  

## 0.0.26

Added:
- Tracking configuration method for location accuracy improvement.

## 0.0.27

Fixed:
- Locations keep updating when device gets idle in distance based tracking.
- Offline trip events order/timestamp.

## 0.0.28

Fixed:
- Location update gap in distance based tracking.
- Stationary noise location update when device gets stationary.

## 0.1.2

Added:

- Course field for offline trips.

## 0.1.3

Added:

- There should be a locations count field.
- Batch update support for trip listener.
- Batch update support for location listener.
- Network listener method for connectivity change.

Modified:

- Create trip method should support create trip without user id.
- Subscribe trip method should support online trip.
- Update trip method should be based on trip state.
- Sync trip should have speed parameter.
- Roam trip status should have trip state as parameter.
- Unsubscribe trip method support for multiple trips.
- Update time stamp field in trip listener.
- Trip status code for control trip .

Fixed:

- Offline trip reached_stop and left stop event are not getting called.
- Drift issue fix.

## 0.0.29

Fixed:
- Location update gap in stationary location update.
- Location update when device gets restart.
- Tracking config logic for time base tracking.

## 0.0.30

Fixed:
- The execution order of MQTT connection and createUser or getUser method's callback.

## 0.1.4

Fixed:
- Noise location update for distance based tracking and stationary location update.

## 0.0.31

Added:
- Support for Roam.initialize() with custom configurations. 

## 0.0.32

Added:
- Meta data support for setDescription method. 

## 0.0.33

Added:
- Magnetic bearing feature in location update listener. 

## 0.1.5

Added:
- Magnetic bearing support.
- Meta data support for setDescription method.
- Tracking config feature.
- App service class parameter to setForegroundNotification method.

## 0.0.34

Added:
- Hourly location count feature.

Fixed:
- The Roam SDK installation support for android 13. 

## 0.1.6

Added:
- Added SDK installation support for android 13.

Fixed:
- Fixed updateCurrentLocation method, RoamPublish param can be null.
- Fixed getActiveTrip method, if isLocal is true then get offline trips in response and vice-versa.
- Fixed getActiveTrip method, trip stops point should be in response.
- Fixed updateTrip method, isLocal param should not be required to update a trip.

Modified:
- Removed isTripSynced method (no longer required).

## 0.1.7

Fixed:
- Fixed getCurrentLocation method issue.

## 0.0.35

Fixed:
- Fixed deprecated code and time out error for get current location method.

## 0.1.8

Fixed:
- Fixed deprecated code and time out error for get current location method.

## 0.1.9

Added:
- Added basic ingest publish topic name for aws cost optimisation.

## 0.1.10

Added:
- Added activity recognition to reduce battery consumption for default tracking modes.

## 0.0.36

Fixed:
- Fixed multiple callback issue for trip methods.

## 0.1.11

Fixed:
- Fixed batch location update.
- Fixed trip sync issue.
- Fixed multiple location update and speed inconsistent issue.

## 0.1.12

Fixed:
- Fixed dynamic distance filter logic.
- Fixed location update gap in time base tracking.

Added:
- Added noise filter in time base tracking.

## 0.1.13

Fixed:
- Changed work manager dependency version for react native SDK support.

## 0.1.14

Added:
- Added post notification support for android 13.

Fixed:
- Removed work manager concept from sdk.

## 0.1.15

Fixed:
- Fixed noise and duplicate locations update.
- Improved location accuracy.

## 0.1.16

Added:
- Added stationary duration for all tracking modes except time base.

## 0.1.17

Added:
- Added a new feature to get PK from local.properties gradle file.

Fixed: 
- Fixed order of callbacks for create and get user methods and MQTT connection.

## 0.1.18

We're pleased to unveil Roam Android SDK version 0.1.18, showcasing an exciting new feature that enhances security and user control. Here's an overview of the latest updates:
- New Security Module: Roam  
In this release, we introduce the Roam Security Module, a powerful addition to the SDK's arsenal. With the Roam module, developers now have access to the toggleSecurity() method, a versatile tool that allows the seamless activation and deactivation of advanced security mechanisms. Prioritizing data security is vital, and the Roam Security Module empowers developers to safeguard sensitive information more effectively.
Your feedback and insights have been instrumental in shaping the Roam Android SDK, and we're grateful for your ongoing support. If you have any questions, suggestions, or need assistance, please don't hesitate to contact our support team. As we strive to continually refine and enhance the SDK, look forward to more updates on the horizon.

## 0.1.19

We're excited to announce the latest release of the Roam Android SDK, version 0.1.19. This update brings a significant enhancement that contributes to heightened security and user control. Read on to discover the key highlights of this release:
- New Feature: Motion Detection Security
With Roam Android SDK v0.1.19, we introduce an innovative security feature—Motion Detection. This cutting-edge capability adds an extra layer of protection to your application's tracking experience. By enabling the verifyMotion parameter in the toggleSecurity() method, developers can now leverage motion patterns to enhance security. This empowers you to monitor and respond to unusual motion activities, ensuring a safer and more reliable tracking environment.
Your feedback and insights have been instrumental in shaping the Roam Android SDK, and we're grateful for your ongoing support. If you have any questions, suggestions, or need assistance, please don't hesitate to contact our support team. As we strive to continually refine and enhance the SDK, look forward to more updates on the horizon.

## 0.1.20

We are thrilled to introduce Roam Android SDK version 0.1.20, a significant step forward in enhancing your tracking experience. 
- Here's what's new:
New Callbacks for Enhanced Control: With added success and error callbacks to methods like startTracking, stopTracking, subscribe, unSubscribe, publishAndSave, and stopPublishing, you now have precise control and real-time feedback for every SDK action.
Extended Security Checks: We've expanded the toggleSecurity() method with two vital parameters. Now, you can check for connected external accessories and Bluetooth status, bolstering security measures for your application.
Your feedback has played a pivotal role in shaping these enhancements. Should you have any questions, encounter issues, or want to share your experiences, our dedicated support team is here to assist you.

## 0.1.21

We're thrilled to present Roam Android SDK version 0.1.21, focusing on bolstering our security module. In this update, we've made significant improvements to enhance the security of location data. Here's what's new:
- Efficient Spoofed Location Detection: Our security algorithm has been refined to efficiently discard spoofed locations during the initial location fix. This enhancement ensures that only genuine and accurate location data is captured, enhancing the authenticity of the information gathered.
- Addressing False Negatives: We've tackled false negatives during location tracking, ensuring that true locations are not mistakenly identified as spoofed locations. This refinement enhances the precision of location tracking, providing you with reliable and accurate data.

These security enhancements are designed to safeguard your data and ensure the integrity of the location information collected by the Roam Android SDK. Your security is our priority, and we're committed to providing you with a secure and trustworthy experience. If you have any questions, concerns, or feedback, our support team is always here to assist you.

## 0.1.22

We're excited to introduce Roam Android SDK version 0.1.22, focusing on refining our location filters to improve accuracy during location tracking. In this update, we've made the following improvement:
- **Enhanced Location Filtering:** We've refined our location filters to effectively discard drifts and noises during location tracking. This enhancement aims to improve the accuracy and reliability of location data, ensuring a more precise representation of user movement.

These improvements are geared towards providing a more accurate and dependable location tracking experience for developers and users. We're dedicated to enhancing your tracking capabilities and ensuring a smoother operational experience. Should you have any inquiries or feedback, our support team is readily available to assist you.

## 0.1.23

We're excited to introduce Roam Android SDK version 0.1.23, focusing on refining our location filters to improve accuracy during location tracking. In this update, we've made the following improvement:
- **Enhanced Location Filtering:** We've refined our location filters to effectively discard drifts and noises during location tracking. This enhancement aims to improve the accuracy and reliability of location data, ensuring a more precise representation of user movement.

These improvements are geared towards providing a more accurate and dependable location tracking experience for developers and users. We're dedicated to enhancing your tracking capabilities and ensuring a smoother operational experience. Should you have any inquiries or feedback, our support team is readily available to assist you.

## 0.1.24

We're excited to introduce Roam Android SDK version 0.1.24, featuring the addition of a new feature called Custom MQTT Connector. This update enables developers to register custom MQTT endpoints to forward location updates directly from the SDK to the configured MQTT broker. Here's what's new:
- **Custom MQTT Connector:** Developers now have the capability to register custom MQTT endpoints, allowing seamless forwarding of location updates from the SDK to the specified MQTT broker. This feature provides greater flexibility and customization options for integrating location data into your applications.

These enhancements expand the capabilities of the Roam Android SDK, empowering developers with more control over how location updates are managed and transmitted. We're committed to providing a versatile and robust SDK experience. For any inquiries or feedback regarding this new feature, our support team is here to assist you.

## 0.1.25

We're excited to introduce Roam Android SDK version 0.1.25, featuring an enhancement to support `Roam.updateLocationWhenStationary()` alongside Activity Recognition-based stationary detection. This update provides developers with more control over location updates when stationary. Here's what's new:
- **Support for Roam.updateLocationWhenStationary():** Developers can now utilize `Roam.updateLocationWhenStationary()` in conjunction with Activity Recognition-based stationary detection. This enhancement allows for more precise management of location updates, particularly in stationary scenarios, enhancing the overall accuracy of location tracking.

We're committed to continually improving the Roam Android SDK to provide developers with powerful tools for location tracking. If you have any questions or feedback regarding this enhancement, our support team is available to assist you.

## 0.1.26

We're thrilled to present Roam Android SDK version 0.1.26, focusing on expanding compatibility with the latest Android development environment. In this update, we've added support for Android SDK 34. Here's what's new:
- **Support for Android SDK 34:** The Roam Android SDK now fully supports Android SDK 34. Developers can leverage the latest features and improvements offered by the newest Android platform, ensuring optimal performance and compatibility.

We're committed to providing developers with cutting-edge tools and resources to streamline their development process. If you have any questions, concerns, or feedback, our support team is always here to assist you.

## 0.1.27

We're thrilled to present Roam Android SDK version 0.1.27, addressing a specific issue with the `Roam.updateLocationWhenStationary()` method on certain devices running Android 14. In this update, we've made the following fix:
- **Fix for `Roam.updateLocationWhenStationary()` on Android 14:** We've resolved an issue where the `updateLocationWhenStationary()` method was not functioning correctly on some devices running Android 14. This fix ensures reliable performance and accurate location updates on all affected devices.

We're committed to providing you with a seamless and robust SDK experience. If you have any questions, concerns, or feedback, our support team is always here to assist you.

## 0.1.28

We're thrilled to present Roam Android SDK version 0.1.28, featuring the addition of two new modules to enhance your location tracking capabilities. In this update, we've introduced the following modules:

**roam-mqtt-connector:** This module enables custom MQTT connections, allowing developers to register custom MQTT endpoints for forwarding location updates directly from the SDK to the configured MQTT broker.

**roam-batch-connector:** This module allows for publishing location data in batches instead of in real-time using the pub/sub model. This enhancement provides flexibility in managing location data transmission and can help optimize performance and data usage.


These new modules are designed to provide greater flexibility and control over location data handling in your applications. We're committed to continually improving the Roam Android SDK to meet your evolving needs. If you have any questions, concerns, or feedback, our support team is always here to assist you.

## 0.1.29

We're thrilled to present Roam Android SDK version 0.1.29, focusing on expanding compatibility and strengthening security. In this update, we've made the following improvements:
- **Support for Android 14:** We've added full support for Android 14, ensuring seamless functionality on the latest Android version.
- **Security Enhancements to Broadcast Receivers:** We've implemented security changes to our broadcast receivers, enhancing the overall security and integrity of your application.


These updates are designed to provide a more secure and compatible experience for developers and users alike. We're dedicated to continually improving the Roam Android SDK to meet your needs. Should you have any inquiries or feedback, our support team is always here to assist you.

## 0.1.30

We're pleased to announce the release of Roam Android SDK version 0.1.30, focusing on enhancing the stability and functionality of the MQTT logging system. In this update, we've made the following improvement:
- **MQTT Logger Method Fix:** We've fixed an issue with the MQTT logger method, ensuring more reliable and accurate logging of MQTT-related activities.

This update is part of our ongoing commitment to improving the Roam Android SDK. If you have any questions or feedback, our support team is always here to assist you.


## 0.1.31

We're pleased to introduce Roam Android SDK version 0.1.31, focusing on ensuring compatibility with the latest Android devices. In this update, we've made the following enhancement:
- **Google API Updates for MQTT Module:** We've updated the Google APIs within our MQTT module to fully support devices running Android 14, ensuring seamless functionality and compatibility.


This update is part of our ongoing effort to keep the Roam Android SDK aligned with the latest technological advancements. If you have any questions or need further assistance, our support team is ready to help.

## 0.1.32

We're excited to present Roam Android SDK version 0.1.32, with key improvements to our distance-based tracking system. In this update, we've addressed the following issues:

**Location Gap Fix:** We've resolved an issue where location gaps occurred during distance-based tracking. This fix ensures smoother and more continuous location data collection, preventing unnecessary gaps in the location trail.

**Reduced Location Drift:** Enhancements have been made to minimize location drift during tracking. This improvement provides more precise and reliable location data, ensuring accuracy even when tracking across varied distances.

These improvements are designed to offer a more consistent and accurate tracking experience. We're dedicated to optimizing your location tracking capabilities. For any questions or feedback, our support team is ready to assist.

## 0.1.33

We're excited to present Roam Android SDK version 0.1.33, focused on optimizing SDK efficiency and improving location accuracy. In this update, we've made the following improvement:

**Extended Visit Tracking Interval:** We've adjusted the visit tracking interval from 30 seconds to 120 seconds. This enhancement ensures more accurate ground truth data while boosting SDK efficiency, reducing resource usage and delivering more reliable location insights.


This update is part of our ongoing commitment to enhancing tracking precision and performance. For any questions or feedback, our support team is available to help.

## 0.1.34-beta.1

We're thrilled to present Roam Android SDK version 0.1.34-beta.1, with updates to optimize SDK efficiency and enhance location accuracy. Here's what's new:

**Extended Visit Tracking Interval:** We've adjusted the visit tracking interval from 120 seconds to 300 seconds. This change allows for more accurate ground truth data and improves SDK efficiency, resulting in lower resource usage and more reliable tracking insights.

These improvements align with our commitment to provide efficient and precise location tracking. For questions or feedback, our support team is always ready to assist.

## 0.1.34

We're thrilled to present Roam Android SDK version 0.1.34, featuring updates to enhance the accuracy of trip summaries. Here's what's new:

**Accurate Distance Information in Trip Summary:** The trip summary now includes precise distance calculations, providing users with more reliable and accurate trip details. This improvement enhances the overall user experience by delivering trustworthy and consistent data.

This update reflects our ongoing commitment to optimizing functionality and improving user satisfaction. If you have any questions or feedback, our support team is always here to help.


## 0.1.35-beta.1

We're thrilled to present Roam Android SDK version 0.1.35-beta.1, introducing new features to improve location accuracy and data collection. 

Here's what's new:

**Horizontal Accuracy Filter:**
Added a horizontal accuracy filter to allow location data to be refined based on a specified accuracy threshold, ensuring higher precision in location tracking.

**Data Enrichment:**
Enhanced data collection capabilities to gather comprehensive device data from both Android and iOS platforms, enabling richer insights.

**Centroid Method:**
Introduced a centroid calculation method to derive a representative central coordinate from clusters of geographical positions, offering a more reliable reference point.


These updates are designed to provide a more accurate and enriched tracking experience. If you have any questions, concerns, or feedback, our support team is always here to assist.

## 0.1.35-beta.2

We're thrilled to present Roam Android SDK version 0.1.35-beta.2, featuring updates to enhance the data enrichment. Here's what's new:

**Data Enrichment:** Enhanced data enrichment support for listener method to gather comprehensive device data from both Android and iOS platforms, enabling richer insights.

These updates are designed to provide a more accurate and enriched tracking experience. If you have any questions, concerns, or feedback, our support team is always here to assist.

## 0.1.35-beta.4

**Enhancements in Data Enrichment and Testing Capabilities**

We're excited to introduce Roam Android SDK version 0.1.35-beta.4, bringing updates to enrich data insights and improve testing flexibility. Here's what's new:

**New Data Enrichment:**

Introduced additional data enrichment features to provide more detailed and valuable device information.


**Increased Minimum SDK Version:**

Updated the minimum SDK version requirement to ensure compatibility with modern Android devices and features.


**New Testing Functions:**

Added several new functions to facilitate testing, making it easier to validate and refine implementation.
These updates are aimed at improving the SDK's capabilities and user experience. If you have any questions, concerns, or feedback, our support team is here to assist.

## 0.1.35-beta.5

We're excited to introduce Roam android SDK version 0.1.35-beta.5, with updates aimed at improving data insights. Here's what's new:
Bug Fixes:
Fixed minor issues to ensure a more stable and seamless integration experience.

## 0.1.35-beta.6

We're excited to introduce Roam android SDK version 0.1.35-beta.6, with updates aimed at improving batch data processing. Here's what's new:
Bug Fixes:
Fixed minor issues to ensure a more stable and seamless integration experience.

## 0.1.35

Enhancements in Data Enrichment and SDK Compatibility

We're excited to introduce Roam Android SDK version 0.1.35, bringing improvements to data enrichment and platform compatibility. Here's what's new:

New Data Enrichment:

Added additional data enrichment features to enhance the quality and depth of collected information.

Updated Minimum SDK Version:

Increased the minimum SDK version to 21 for better compatibility with modern Android features.
These updates aim to provide a more efficient and reliable experience. If you have any questions or feedback, our support team is here to assist.

## 0.1.36

**Enhancements in Data Customization**

We're thrilled to present Roam Android SDK version 0.1.36, introducing new enhancements for greater customization. Here's what's new:

**Data Enhancements Additions:**
Introduced several new parameters to provide more flexibility and control over data handling.

These updates aim to enhance the SDK's customization capabilities and overall user experience. If you have any questions or feedback, our support team is always here to assist you.

## 0.1.37

We're excited to introduce Roam Android SDK version 0.1.37, bringing key improvements and updates. 

Here's what's new:

- Fixed callback issue for getting and creating users, ensuring smoother functionality.

- Added consumer ProGuard rules to support code shrinking and optimization.

- Updated Retrofit library to version 2.11.0 for improved network handling.

- Upgraded AGP version to 8.2.0 and Gradle version to 8.3, enhancing build performance and compatibility.

These updates aim to improve performance, security, and overall developer experience. If you have any questions or feedback, our support team is always here to assist you.

## 0.1.38

Introducing Local Geofence Module

We're thrilled to announce Roam Android SDK version 0.1.38, bringing powerful new geofencing capabilities right to your device — fully offline and network-independent. Here's what's new:

✅ Fully Offline / Local Geofence Module: Enjoy geofencing capabilities without any network dependency.

✏️ Geofence Management Methods: Easily create, update, retrieve, and delete geofences within your app.

📍 Real-time Geofence Events: Instantly detect entry and exit events for precise location triggers.

📊 High-capacity Geofence Handling: Efficiently manage 10,000+ geofences without impacting app performance.

This update empowers developers to deliver more responsive and flexible location-aware experiences. For questions or feedback, our support team is always here to help!

## 0.1.39

Introducing Local Geofence Module

We're thrilled to announce Roam Android SDK version 0.1.39, bringing powerful new geofencing capabilities right to your device — fully offline and network-independent. Here's what's new:

✅ Fully Offline / Local Geofence Module: Enjoy geofencing capabilities without any network dependency.

✏️ Geofence Management Methods: Easily create, update, retrieve, and delete geofences within your app.

📍 Real-time Geofence Events: Instantly detect entry and exit events for precise location triggers.

📊 High-capacity Geofence Handling: Efficiently manage 10,000+ geofences without impacting app performance.

This update empowers developers to deliver more responsive and flexible location-aware experiences. For questions or feedback, our support team is always here to help!


## 0.1.40

Enhancements in Data Enrichment and Performance

We're excited to introduce Roam Android SDK version 0.0.1.40, focusing on expanding data enrichment capabilities and boosting performance. In this update, we've made the following improvements:

New Data Enrichment Fields: We've added additional fields to the data enrichment layer, allowing you to access more comprehensive and valuable location insights.

Performance Improvements: Optimized internal processes for faster execution and improved efficiency, ensuring a smoother experience for both developers and end users.

These updates are aimed at enhancing the depth and reliability of location data, while also improving the SDK's performance. As always, our support team is here to help with any questions or feedback you may have.

## 0.1.41-beta.2

- Release as Beta Version
- SDK Improvement
- New Data Enrichment Fields Added.

## 0.1.41-beta.3

- Release as Beta Version
- SDK Improvement
- New Data Enrichment Fields Added.

## 0.1.41-beta.4

- Release as Beta Version
- SDK Improvement
- New Data Enrichment Fields Added.

## 0.1.41

Enhancements in Data Enrichment, Performance, and Tracking Quality

We're excited to introduce Roam Android SDK version 0.1.41, bringing key improvements that elevate the SDK's capabilities in data richness, performance, and tracking precision. Here's what's new:

- New Data Enrichment Fields: We've added additional fields to provide deeper and more insightful location data.
- SDK Optimisation: Core components have been optimised to reduce overhead and enhance efficiency during runtime.
- Improved Tracking Quality: Refined algorithms now deliver more accurate and consistent location tracking, even in complex environments.

These updates are designed to deliver a smoother and more insightful experience for developers and users alike. If you have any questions, feedback, or need assistance, our support team is always here to help.

## 0.1.42

Enhancements in Data Enrichment, Debugging, and Permission Handling

We're excited to introduce Roam Android SDK version 0.1.42, bringing new capabilities to improve data insights, diagnostics, and flexibility in permission management. Here's what's new:

**New Data Enrichment Fields:** Added additional enrichment parameters to provide deeper and more valuable location context.

**Enhanced Debug and Diagnostic Logs:** Introduced detailed logs to aid in better debugging and issue tracking during development.

**Optional Permissions:** Certain permissions are now optional, giving developers more control based on app-specific use cases.

These improvements are designed to offer greater flexibility, visibility, and precision within your location-based applications. For questions or support, our team is always here to help.
