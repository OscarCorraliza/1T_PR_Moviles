package com.example.practicamoviles_1tr.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

public class GymFragment extends Fragment implements Serializable {

    private List<MapPoint> mapPoints, mapPointsFavs;
    private ListView listView;
    private MapPointAdapter adapter = null;
    private FavsSettings favsSettings;
    private MapPoint mapPoint;
    private double longitude, latitude;

    public GymFragment(double longitude, double latitude) {
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
        listView = getActivity().findViewById(R.id.lvMapPoints);
        getGyms();
        favsSettings = new FavsSettings(getActivity());

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            mapPoint = mapPoints.get(position);
            favsSettings.setFav(mapPoint);
            ImageView favImg = view.findViewById(R.id.favIcon);
            favImg.setImageResource(R.drawable.ic_star_on);
            return false;
        });
    }

    public void getGyms(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENTRY_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IfaceApi ifaceApi = retrofit.create(IfaceApi.class);

        ifaceApi.getGyms(latitude, longitude, DISTANCE).enqueue(new Callback<JsonResponse>() {

            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if(response!=null && response.body() != null){
                    mapPoints = response.body().results;
                    mapPointsFavs = new FavsSettings(getActivity()).getFavs();
                    //bucle para recuperar todos los mappoints con los favoritos que ya haya(para tener el atributo isFav)
                    for (int i=0; i<mapPoints.size();i++){
                        for (MapPoint fav:mapPointsFavs){
                            if(mapPoints.get(i).getTitle().equalsIgnoreCase(fav.getTitle())){
                                mapPoints.set(i, fav);
                            }
                        }
                    }
                    adapter=new MapPointAdapter(getContext(), mapPoints, getActivity());
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call <JsonResponse>  call, Throwable t) {}
        });
    }

}
