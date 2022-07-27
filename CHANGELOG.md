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
