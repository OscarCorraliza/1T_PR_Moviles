package com.example.practicamoviles_1tr.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practicamoviles_1tr.R;


public class MyLocationFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // asiganamos la vista del layout de este fragmento
        final View view = inflater.inflate(R.layout.fragment_my_position, container, false);



        return view;
    }
}