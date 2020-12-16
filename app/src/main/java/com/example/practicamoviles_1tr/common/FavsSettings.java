package com.example.practicamoviles_1tr.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.fragments.FavouritesFragment;
import com.example.practicamoviles_1tr.models.Location;
import com.example.practicamoviles_1tr.models.MapPoint;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;
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
        ArrayList<MapPoint> mapPoints=new ArrayList<>();
        JsonParser parser = new JsonParser();

        if(json==null){
            return new ArrayList<>();
        }

        //comprobar si el json es un array o un objeto solo
        if(parser.parse(json).isJsonArray()){
            //inicializamos el parser y le metemos el string del json
            JsonArray arrayGson = parser.parse(json).getAsJsonArray();
            //recorremos el array en el json
            for (JsonElement obj:arrayGson){
                JsonObject gsonMapPoint = obj.getAsJsonObject();

                JsonObject gsonLocation = gsonMapPoint.get("location").getAsJsonObject();

                double latitude = gsonLocation.get("latitude").getAsDouble();
                double longitude = gsonLocation.get("longitude").getAsDouble();

                String title = gsonMapPoint.get("title").getAsString();
                Location location = new Location(latitude, longitude);
                MapPoint mapPoint = new MapPoint(title, location, true);
                mapPoints.add(mapPoint);
            }
        }else{
            JsonObject objJson = parser.parse(json).getAsJsonObject();
            JsonObject locationJson = objJson.get("location").getAsJsonObject();
            double latitude = locationJson.get("latitude").getAsDouble();
            double longitude = locationJson.get("longitude").getAsDouble();

            String title = objJson.get("title").getAsString();
            Location location = new Location(latitude, longitude);
            MapPoint mapPoint = new MapPoint(title, location, true);

            mapPoints.add(mapPoint);
        }

        return mapPoints;
    }
    public void setFav(MapPoint newFav){
        SharedPreferences preferences = context.getSharedPreferences(CLAVE_PREFERENCES, MODE_PRIVATE);
        String json = preferences.getString(CLAVE_PREFERENCES_ARRAY, null);

        ArrayList<MapPoint> favs;
        String arrGson;
        Gson gson = new Gson();
        if(json!=null){
            boolean repeat=false;
            favs = getFavs();

            for(MapPoint mp:favs){
                if(mp.getTitle().equalsIgnoreCase(newFav.getTitle())){
                    repeat=true;
                }
            }
            if(!repeat){
                favs.add(newFav);
                arrGson = gson.toJson(favs);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(CLAVE_PREFERENCES_ARRAY, arrGson);
                editor.apply();
                Toast toastRepeat = Toast.makeText(context, R.string.newFav, LENGTH_SHORT);
                toastRepeat.show();
            }else{
                Toast toastRepeat = Toast.makeText(context, R.string.favSave, LENGTH_SHORT);
                toastRepeat.show();
            }

        }else{
            arrGson = gson.toJson(newFav);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(CLAVE_PREFERENCES_ARRAY, arrGson);
            editor.apply();
            Toast toastRepeat = Toast.makeText(context, R.string.newFav, LENGTH_SHORT);
            toastRepeat.show();
        }
    }

    public void removeFav(int position){
        Gson gson = new Gson();

        ArrayList<MapPoint> favs = getFavs();
        //volvemos a hacer false la variable que indica si un mappoint es favorito
        favs.get(position).setFav(false);
        favs.remove(position);
        String arrGson = gson.toJson(favs);

        SharedPreferences preferences = context.getSharedPreferences(CLAVE_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CLAVE_PREFERENCES_ARRAY, arrGson);
        editor.apply();
    }
}
