# godot-location-plugin-android

Godot plugin to get current location on Android

To use this plugin you will need to compile it against your required version of Godot.

### Compile

You will need:

-   Android Studio https://developer.android.com/studio
-   Godot AAR Library for your desired version https://godotengine.org/download/

1. Open the project in Android Studio
2. Put your downloaded Godot AAR Library into the `app/libs` directory. (Ensure its filename matches `godot-lib*.aar`)
3. Compile the project

### Install Plugin

1. Copy the `app/build/outputs/aar/LocationPlugin.aar` file and the `app/LocationPlugin.gdap` file to the `android/plugins` folder in your Godot app directory
2. Go to Project -> Export, select the android export, check custom build, and enable the plugin

### Get the current location

```
extends Node2D

func _ready():
	// Load The plugin
    var locationPlugin = Engine.get_singleton("LocationPlugin")

    // Connect to the response signal
	locationPlugin.connect("onCurrentLocationResponse", on_CurrentLocationResponse)

    // request the current Location
	locationPlugin.requestCurrentLocation()

func on_CurrentLocationResponse(locationData):
	// Print the location details: { "latitude": 12.3456789, "longitude": 12.3456789 }
    print(locationData)

```
