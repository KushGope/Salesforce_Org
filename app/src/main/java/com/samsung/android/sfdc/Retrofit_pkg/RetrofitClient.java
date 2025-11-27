package com.samsung.android.sfdc.Retrofit_pkg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private ApiInterface apiInterface;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiInterface.BASE_URL)
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static synchronized RetrofitClient getInstance() {

        if (instance == null)
            instance = new RetrofitClient();

        return instance;
    }

    public ApiInterface getJsonApi() {
        return apiInterface;
    }
}
