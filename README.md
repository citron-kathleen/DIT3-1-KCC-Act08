GPS Location Tracker App

Project Overview
- This Android application displays the user's live GPS location on a map using OpenStreetMap and device location services. Built with Kotlin and XML Layouts (standard View System). No API Key Required! No Credit Card Needed!

Features Implemented
✅ Location Permission Request and Handling - Properly requests and handles runtime location permissions (Precise/Approximate).
✅ Map Displayed on Screen - OpenStreetMap integration using osmdroid.
✅ Marker Showing Current Location - Custom marker displaying real-time GPS coordinates.
✅ Location Updates - Real-time tracking using Fused Location Provider.
✅ Basic UI (Single Screen) - Clean interface with a location information card.

Technical Implementation

Dependencies Added
- osmdroid-android (6.1.18) - OpenStreetMap library for Android.
- play-services-location (21.1.0) - Fused Location Provider for efficient tracking.

Permissions
The app requests these permissions in AndroidManifest.xml:
- ACCESS_FINE_LOCATION - For precise GPS location.
- ACCESS_COARSE_LOCATION - For approximate location (required by Android 12+).
- INTERNET - To download map tiles.
- ACCESS_NETWORK_STATE - To check connectivity.

Key Components
1. OpenStreetMap (osmdroid)
- Uses free and open-source maps: MAPNIK tile source.
- Multi-touch controls and zoom level: 18.0.
- No API Key required for tile loading.

2. FusedLocationProviderClient
- Uses Google Play Services for battery-friendly updates.
- Priority: PRIORITY_HIGH_ACCURACY (GPS).

3. Location Permission Flow
- App checks for permission on launch.
- If not granted, displays the Precise/Approximate dialog.
- On Grant: Starts location updates and moves camera to user.

Setup Instructions
- No API Key Required! 🎉 Just sync and run.
- Sync and Build: Open in Android Studio, click "Sync Project with Gradle Files".
- Run: Build and run on a physical device or emulator.
- Internet Required: Needed to download map tiles initially.

Testing
- On Physical Device: Install, grant permissions when prompted, and move around to see the marker update.
- On Emulator: Use Extended Controls → Location to manually set coordinates or playback a route.

Project Structure
app/src/main/java/com/example/gpstracker/
├── MainActivity.kt           # Main activity with location and permission logic
└── (Layouts)
    └── activity_main.xml     # UI with MapView and Info Card


Location not updating: Ensure GPS is ON and permissions are granted.

Build errors: Run Clean Project and Rebuild Project.
