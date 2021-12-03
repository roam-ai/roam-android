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
Added support to listen to location updates of user from different projects which are within same account.

## 0.0.8

Added:
Added support to listen to location permision status in receiver class when location permission is changed.
