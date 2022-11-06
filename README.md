# godot-location-plugin-android

Godot plugin to get current location on Android

To use this plugin you will need to compile it against your required version of Godot.

### Compile

You will need:

-   Android Studio https://developer.android.com/studio
-   Godot AAR Library for your desired version https://godotengine.org/download/

1. Open the project in Android Studio
2. Put your downloaded Godot AAR Library into the `app/libs` directory. (Ensure its filename matches `godot-lib*.aar`)

![Screen Shot 2022-11-06 at 2 16 18 pm](https://user-images.githubusercontent.com/657135/200154507-c0289657-b8ea-48cf-a414-fed773c19586.png)

3. Compile the project

### Install Plugin

1. Copy the `app/build/outputs/aar/LocationPlugin.aar` file and the `app/LocationPlugin.gdap` file to the `android/plugins` folder into your Godot app directory.

![Screen Shot 2022-11-06 at 2 17 28 pm](https://user-images.githubusercontent.com/657135/200154515-83f6ca62-f89d-48d1-bc3c-c13e99f39bd0.png)

2. Go to Project -> Export, select the android export, check custom build, and enable the plugin.

![Screen Shot 2022-11-06 at 2 18 37 pm](https://user-images.githubusercontent.com/657135/200154518-69750a9b-77c1-49a6-afc4-8f5030d12fa4.png)


### Get the current location

```gdscript
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
