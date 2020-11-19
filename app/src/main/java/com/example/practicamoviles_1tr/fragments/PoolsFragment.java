package com.example.practicamoviles_1tr.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.practicamoviles_1tr.R;
import com.example.practicamoviles_1tr.api_manager.IfaceApi;
import com.example.practicamoviles_1tr.api_manager.JsonResponse;
import com.example.practicamoviles_1tr.common.PoolsAdapter;
import com.example.practicamoviles_1tr.models.Pools;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.practicamoviles_1tr.common.Constantes.ENTRY_POINT;

public class PoolsFragment extends Fragment {
    private List<Pools> listPools;
    private ListView listView =null;
    PoolsAdapter adapter = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pools, container, false);


        return view;
    }

    public void getAllPokemon(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENTRY_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IfaceApi ifaceApi = retrofit.create(IfaceApi.class);

        ifaceApi.getPools().enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {
                if(response!=null && response.body() != null){
                    listPools = response.body().results;

                    adapter=new PoolsAdapter(getContext(), listPools);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

            }
        });
    }
}