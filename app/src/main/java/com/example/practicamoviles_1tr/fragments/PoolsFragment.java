 package com.example.practicamoviles_1tr.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.api_manager.IfaceApi;
import com.example.practicamoviles_1tr.api_manager.JsonResponse;
import com.example.practicamoviles_1tr.common.MapPointAdapter;
import com.example.practicamoviles_1tr.models.MapPoint;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.practicamoviles_1tr.common.Constantes.ENTRY_POINT;

public class PoolsFragment extends Fragment implements Serializable {

    private List<MapPoint> mapPoints;
    private ListView listView;
    MapPointAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mappoints, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView)getActivity().findViewById(R.id.lvMapPoints);
        getPools();
    }

    public void getPools(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENTRY_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IfaceApi ifaceApi = retrofit.create(IfaceApi.class);

        ifaceApi.getPools().enqueue(new Callback<JsonResponse>() {

            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if(response!=null && response.body() != null){
                    mapPoints = response.body().results;

                    for(MapPoint m:mapPoints){
                        System.out.println(m.getTitle());
                    }
                    adapter=new MapPointAdapter(getContext(), mapPoints);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call <JsonResponse>  call, Throwable t) {
                Log.d("Fallo", "Entra por el fallo");
                t.printStackTrace();
            }
        });
    }
}