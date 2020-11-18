package com.example.practicamoviles_1tr.models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonResponse {
    @SerializedName("results")
    @Expose
    public final List<Pools> results=null;

}
