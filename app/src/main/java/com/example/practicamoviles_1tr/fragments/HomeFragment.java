package com.example.practicamoviles_1tr.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.practicamoviles_1tr.R;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_PREFERENCES;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_SAVELOCATION;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_SAVELOCATION_LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.CLAVE_SAVELOCATION_LONGITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.MAPPOINT_LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.MAPPOINT_LONGITUDE;


public class HomeFragment extends Fragment {
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = getActivity().findViewById(R.id.btnPosition);
        button.setOnClickListener(v -> {
            SharedPreferences preferences = getActivity().getSharedPreferences(CLAVE_SAVELOCATION, MODE_PRIVATE);
            double latitude = preferences.getFloat(CLAVE_SAVELOCATION_LATITUDE, 0);
            double longitude = preferences.getFloat(CLAVE_SAVELOCATION_LONGITUDE, 0);

            if(latitude == 0 && longitude == 0){
                Toast toastRepeat = Toast.makeText(getActivity(), R.string.location, LENGTH_SHORT);
                toastRepeat.show();
            } else{
                FragmentManager manager = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();

                bundle.putDouble(MAPPOINT_LATITUDE, latitude);
                bundle.putDouble(MAPPOINT_LONGITUDE, longitude);

                Fragment fragment = new MapPointMap();
                fragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            }
        });
    }
}