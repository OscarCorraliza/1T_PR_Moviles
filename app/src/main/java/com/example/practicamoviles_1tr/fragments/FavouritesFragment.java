package com.example.practicamoviles_1tr.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.common.FavsSettings;
import com.example.practicamoviles_1tr.common.MapPointAdapter;
import com.example.practicamoviles_1tr.models.MapPoint;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FavouritesFragment extends Fragment  {

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
        lvFavs = getActivity().findViewById(R.id.lvFavs);
        favsList = new FavsSettings(getActivity()).getFavs();
        adapter=new MapPointAdapter(getContext(), favsList, getActivity());
        lvFavs.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvFavs.setOnItemLongClickListener((parent, view, position, id) -> {
            showDialog(position);
            //refrescamos el adapter
            favsList = new FavsSettings(getActivity()).getFavs();
            adapter=new MapPointAdapter(getContext(), favsList, getActivity());
            lvFavs.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return false;
        });
    }



    public void showDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.tituloAlert);
        builder.setMessage(R.string.mensajeAlert);

        builder.setPositiveButton(R.string.optSiAlert, (dialog, which) -> {
            new FavsSettings(getActivity()).removeFav(position);
            favsList = new FavsSettings(getActivity()).getFavs();
            adapter=new MapPointAdapter(getContext(), favsList, getActivity());
            lvFavs.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
        builder.setNegativeButton(R.string.optNoAlert, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}