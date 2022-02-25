# Migration Guide

## Roam 0.0.x to Roam 0.1.x

- The Roam.subscribeTripStatus() method has been renamed to Roam.subscribeTrip() 
- The Roam.updateTrip() method is newly added.
- The Roam.stopTrip() method has been renamed to Roam.endTrip() 
- All the below trips method will use the new trips v2 logic where origins and destinations are called as stops.
    - Roam.createTrip()
    - Roam.updateTrip()
    - Roam.deleteTrip()
    - Roam.getTrip()
    - Roam.getActiveTrips()
    - Roam.startTrip()
    - Roam.pauseTrip()
    - Roam.resumeTrip()
    - Roam.endTrip()
    - Roam.syncTrip()
- The trip methods now returns `RoamTripCallback()` with onSuccess and onError override methods on call of below methods:
    - Roam.createTrip()
    - Roam.updateTrip()
    - Roam.getTrip()
    - Roam.startTrip()
    - Roam.pauseTrip()
    - Roam.resumeTrip()
    - Roam.endTrip()
- The trip methods now returns `RoamActiveTripsCallback()` with onSuccess and onError override methods on call of Roam.getActiveTrips()
- The trip methods now returns `RoamDeleteTripCallback()` with onSuccess and onError override methods on call of Roam.deleteTrip()
- The trip methods now returns `RoamSyncTripCallback()` with onSuccess and onError override methods on call of Roam.syncTrip()

## GeoSpark 3.1.x to Roam 0.0.x

- All the method of GeoSpark 3.1.x have been changed from `GeoSpark.METHOD_NAME` to `Roam.METHOD_NAME` in Roam 0.0.x
- The method names and the functionalities remains same.

## Roam 0.0.3 to Roam 0.0.4

- `Roam.startSelfTracking()` and `Roam.stopSelfTracking()` are removed and converted to `Roam.startTracking()` and `Roam.stopTracking()`.
- The method names are merged together and the functionalities remains same.
