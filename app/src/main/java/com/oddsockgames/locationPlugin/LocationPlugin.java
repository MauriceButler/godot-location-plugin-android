package com.oddsockgames.locationPlugin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.ArraySet;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.Task;

import org.godotengine.godot.Dictionary;
import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import java.util.Set;

public class LocationPlugin extends GodotPlugin  {
    private static final int PERMISSION_REQUEST_CODE = 1;

    private Context context;
    private Activity activity;

    public LocationPlugin(Godot godot) {
        super(godot);
        context = godot.getContext();
        activity = godot.getActivity();
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "LocationPlugin";
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();
        signals.add(new SignalInfo("onCurrentLocationResponse", Dictionary.class));
        return signals;
    }


    @UsedByGodot
    public void requestCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
            Task<Location> currentLocationTask = fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null);

            currentLocationTask.addOnCompleteListener(task -> {
                Dictionary result = new Dictionary();

                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    result.put("latitude", location.getLatitude());
                    result.put("longitude", location.getLongitude());
                } else {
                    Exception exception = task.getException();
                    result.put("error", "Exception : " + exception);
                }

                emitSignal("onCurrentLocationResponse", result);
            });
        } else {
            ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onMainRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestCurrentLocation();
            }
        }
    }
}
