package com.example.practicamoviles_1tr.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.practicamoviles_1tr.R;
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

public class FavouritesFragment extends Fragment {

    private ArrayList<MapPoint> favsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    public ArrayList<MapPoint> getFavs(){
        SharedPreferences preferences = getActivity().getSharedPreferences(CLAVE_PREFERENCES, MODE_PRIVATE);
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
        return mapPoints;
    }

    public void setFav(MapPoint newFav){
        ArrayList<MapPoint> favs = getFavs();
        favs.add(newFav);
        Gson gson = new Gson();
        String arrGson = gson.toJson(favs);

        SharedPreferences preferences = getActivity().getSharedPreferences(CLAVE_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLAVE_PREFERENCES_ARRAY, arrGson);
        editor.apply();
    }
}