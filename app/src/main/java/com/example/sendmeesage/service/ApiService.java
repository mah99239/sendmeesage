package com.example.sendmeesage.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAByBmaGI:APA91bGJHmfbfim1f85O3EVDKvkma5H9csbT0pgJk2fQ_K_G_FrWJUEKtWYF9Y8-DrboVxkMW2hlwbpBiY_Oqb47PRx1Q4nQS_hyVgELxd5thZyOhnz1aLo1wMXvBb78pzlGFjwePhou"
    })
    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
