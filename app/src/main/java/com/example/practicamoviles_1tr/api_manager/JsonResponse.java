package com.example.practicamoviles_1tr.api_manager;


import com.example.practicamoviles_1tr.models.*;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonResponse {
    @SerializedName("title")
    @Expose
    public final List<MapPoint> results=null;

}
