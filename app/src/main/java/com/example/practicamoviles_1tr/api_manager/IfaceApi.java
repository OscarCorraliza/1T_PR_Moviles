package com.example.practicamoviles_1tr.api_manager;

import com.example.practicamoviles_1tr.models.MapPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import static com.example.practicamoviles_1tr.common.Constantes.END_POINT_POOLS;
import static com.example.practicamoviles_1tr.common.Constantes.END_POINT_SPORTS;

public interface IfaceApi {
    @Headers({"Accept: application/json , Content-Type: application/json"})
    @GET(END_POINT_POOLS)
    Call<JsonResponse> getPools();

    @Headers({"Accept: application/json , Content-Type: application/json"})
    @GET(END_POINT_SPORTS)
    Call<JsonResponse> getGyms();
}
