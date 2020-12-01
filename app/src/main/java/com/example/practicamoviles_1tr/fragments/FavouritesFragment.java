package com.example.practicamoviles_1tr.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.models.MapPoint;

import java.util.ArrayList;

import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_PREFERENCES;

public class FavouritesFragment extends Fragment {

    private ArrayList<MapPoint> favsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    public ArrayList<MapPoint> getFavs(){
        SharedPreferences preferences = getActivity().getSharedPreferences(CLAVE_PREFERENCES, Context.MODE_PRIVATE);

        return favsList;
    }
}