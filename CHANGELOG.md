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
