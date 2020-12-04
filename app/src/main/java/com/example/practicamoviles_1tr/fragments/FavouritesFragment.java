package com.example.practicamoviles_1tr.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.common.FavsSettings;
import com.example.practicamoviles_1tr.common.MapPointAdapter;
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
    private ListView lvFavs;
    private MapPointAdapter adapter = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvFavs = (ListView) getActivity().findViewById(R.id.lvFavs);
        favsList = new FavsSettings(getActivity()).getFavs();
        adapter=new MapPointAdapter(getContext(), favsList);
        lvFavs.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}