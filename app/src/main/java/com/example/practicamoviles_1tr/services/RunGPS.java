package com.example.practicamoviles_1tr.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.example.practicamoviles_1tr.common.Constantes.INTENT_LOCALIZATION_ACTION;
import static com.example.practicamoviles_1tr.common.Constantes.LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.LONGITUDE;

public class RunGPS extends Service  implements LocationListener{
    private LocationManager locationManager;
    Double latitude=0.0;
    Double longitude=0.0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startLocation();
        return START_STICKY;
    }

    @SuppressLint("MissingPermission")
    private void startLocation() {
        boolean gpsProvider=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkProvider=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!networkProvider) {
            Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            callGPSSettingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(callGPSSettingIntent);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 100,  this);

        }
        if(!gpsProvider){
            Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            callGPSSettingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(callGPSSettingIntent);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 100,  this);

        }
        if(networkProvider){
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 100,  this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 100,  this);

        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Intent intent = new Intent(INTENT_LOCALIZATION_ACTION);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
