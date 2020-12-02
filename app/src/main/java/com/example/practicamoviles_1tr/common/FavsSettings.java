package com.example.practicamoviles_1tr.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.practicamoviles_1tr.models.Location;
import com.example.practicamoviles_1tr.models.MapPoint;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_PREFERENCES;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_PREFERENCES_ARRAY;

//clase con metodos para obtener y guardar sharedpreferences. Le pasamos un context obligatorio para el sharedpreferences
public class FavsSettings {
    private final Context context;
    public FavsSettings(Context context){
        this.context=context;
    }

    public ArrayList<MapPoint> getFavs(){
        SharedPreferences preferences = context.getSharedPreferences(CLAVE_PREFERENCES, MODE_PRIVATE);
        String json = preferences.getString(CLAVE_PREFERENCES_ARRAY, null);

        ArrayList<MapPoint> mapPoints = new ArrayList<>();
        //inicializamos el parser y le metemos el string del json
        JsonParser parser = new JsonParser();
        JsonArray arrayGson = parser.parse(json).getAsJsonArray();
        //recorremos el array en el json
        for (JsonElement obj:arrayGson){
            JsonObject gsonMapPoint = obj.getAsJsonObject();

            String title = gsonMapPoint.get("title").getAsString();
            JsonObject gsonLocation = gsonMapPoint.get("location").getAsJsonObject();

            double latitude = gsonLocation.get("latitude").getAsDouble();
            double longitude = gsonLocation.get("longitude").getAsDouble();

            Location location = new Location(latitude, longitude);
            MapPoint mapPoint = new MapPoint(title, location);
            mapPoints.add(mapPoint);
        }
        for(MapPoint mp:mapPoints){
            System.out.println(mp.getTitle());
        }
        return mapPoints;
    }
    public void setFav(MapPoint newFav){
        ArrayList<MapPoint> favs = getFavs();
        favs.add(newFav);
        Gson gson = new Gson();
        String arrGson = gson.toJson(favs);

        SharedPreferences preferences = context.getSharedPreferences(CLAVE_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLAVE_PREFERENCES_ARRAY, arrGson);
        editor.apply();
    }
}
