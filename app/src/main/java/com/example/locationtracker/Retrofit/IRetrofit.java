package com.example.locationtracker.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IRetrofit
{


    @Headers({"Accept:application/json"})
    @PUT("api/newLocation/")
    Call<JsonObject> update_location(@Body  JsonObject object);
}
