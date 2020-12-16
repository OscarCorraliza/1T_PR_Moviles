package com.example.practicamoviles_1tr.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.common.MapPointAdapter;
import com.example.practicamoviles_1tr.common.WebViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.practicamoviles_1tr.common.Constantes.MAPPOINT_LATITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.MAPPOINT_LONGITUDE;
import static com.example.practicamoviles_1tr.common.Constantes.URL_BUNDLE;
import static com.example.practicamoviles_1tr.common.Constantes.WEB_1;
import static com.example.practicamoviles_1tr.common.Constantes.WEB_2;
import static com.example.practicamoviles_1tr.common.Constantes.WEB_3;

public class WebSitesFragment extends Fragment {

    private ListView webs;
    private List<String> webList = new ArrayList<>();
    private WebViewAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_sites, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webList.add(WEB_1);
        webList.add(WEB_2);
        webList.add(WEB_3);
        webs = getActivity().findViewById(R.id.id_lvWeb);
        adapter=new WebViewAdapter(getContext(), webList);
        webs.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        webs.setOnItemClickListener((parent, view, position, id) -> {
            FragmentManager manager =  getActivity().getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putString(URL_BUNDLE, webList.get(position));
            Fragment fragment = new WebView();
            fragment.setArguments(bundle);
            manager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
        });
    }
}