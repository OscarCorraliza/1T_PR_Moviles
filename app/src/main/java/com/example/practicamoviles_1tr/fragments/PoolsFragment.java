 package com.example.practicamoviles_1tr.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.api_manager.IfaceApi;
import com.example.practicamoviles_1tr.api_manager.JsonResponse;
import com.example.practicamoviles_1tr.common.FavsSettings;
import com.example.practicamoviles_1tr.common.MapPointAdapter;
import com.example.practicamoviles_1tr.models.MapPoint;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.practicamoviles_1tr.common.Constantes.DISTANCE;
import static com.example.practicamoviles_1tr.common.Constantes.ENTRY_POINT;

public class PoolsFragment extends Fragment implements Serializable {

    private ImageView imgFav;
    private List<MapPoint> mapPoints;
    private List<MapPoint> mapPointsFavs;
    private ListView listView;
    private MapPointAdapter adapter = null;
    private FavsSettings favsSettings;
    private MapPoint mapPoint;
    private double longitude, latitude;

    public PoolsFragment(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mappoints, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getActivity().findViewById(R.id.lvMapPoints);
        getPools();
        favsSettings = new FavsSettings(getActivity());

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mapPoint = mapPoints.get(position);
                favsSettings.setFav(mapPoint);
                System.out.println(mapPoint.getTitle());
                return false;
            }
        });
    }

    public void getPools(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENTRY_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IfaceApi ifaceApi = retrofit.create(IfaceApi.class);

        ifaceApi.getPools(latitude, longitude, DISTANCE).enqueue(new Callback<JsonResponse>() {

            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if(response!=null && response.body() != null){
                    mapPoints = response.body().results;
                    mapPointsFavs = new FavsSettings(getActivity()).getFavs();

                    //bucle para recuperar todos los mappoints con los favoritos que ya haya(para tener el atributo isFav)
                    for (MapPoint mp:mapPoints){
                        for (MapPoint fav:mapPointsFavs){
                            if(mp.getTitle().equalsIgnoreCase(fav.getTitle())){
                                mp=fav;
                            }
                        }
                    }

                    adapter=new MapPointAdapter(getContext(), mapPoints);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call <JsonResponse>  call, Throwable t) {}
        });
    }
}