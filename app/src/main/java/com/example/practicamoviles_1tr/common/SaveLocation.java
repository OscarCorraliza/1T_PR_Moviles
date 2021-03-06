package com.example.practicamoviles_1tr.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.practicamoviles_1tr.models.Location;

import static android.content.Context.MODE_PRIVATE;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_PREFERENCES;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_PREFERENCES_ARRAY;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_SAVELOCATION;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_SAVELOCATION_LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_SAVELOCATION_LONGITUDE;

public class SaveLocation {
    private final Context context;
    private double latitude, longitude;

    public SaveLocation(Context context, double latitude, double longitude){
        this.context = context;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void saveLocation(){
        SharedPreferences preferences = context.getSharedPreferences(CLAVE_SAVELOCATION, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(CLAVE_SAVELOCATION_LONGITUDE, (float) longitude);
        editor.putFloat(CLAVE_SAVELOCATION_LATITUDE, (float) latitude);
        editor.apply();
    }
}
