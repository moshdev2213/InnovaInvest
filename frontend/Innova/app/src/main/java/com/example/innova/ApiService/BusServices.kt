package com.example.charmrides.ApiService

import com.example.charmrides.EntityRes.BusRes
import com.example.charmrides.EntityRes.PayRes
import com.example.charmrides.EntityRes.PaymentItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BusServices {
    @GET("/api/collections/bus/records")
    fun getAlBuses(): Call<BusRes>

    @POST("/api/collections/payment/records")
    fun insertPay(
        @Body paymentItem: PaymentItem
    ):Call<PaymentItem>

    @GET("/api/collections/payment/records")
    fun getAllPay(
        @Query("filter")filter : String
    ):Call<PayRes>

}