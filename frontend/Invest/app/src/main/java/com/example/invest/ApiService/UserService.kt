package com.example.invest.ApiService

import com.example.innova.EntityRes.Record
import com.example.invest.EntityReq.UpdateUser
import com.example.invest.EntityRes.CountStat
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("/api/collections/users/records/{id}")
    fun getUserDetails(
        @Path("id") id:String
    ): Call<Record>

    @GET("/api/collections/investor/records/{id}")
    fun getInvestorDetails(
        @Path("id") id:String
    ): Call<Record>
    @PATCH("/api/collections/investor/records/{id}")
    fun updateInvestorData(
        @Path("id")id:String,
        @Body updateUser: UpdateUser
    ):Call<Record>


    @PATCH("/api/collections/users/records/{id}")
    fun updateUserData(
        @Path("id")id:String,
        @Body updateUser: UpdateUser
    ):Call<Record>

    @GET("/api/collections/proposal/records")
    fun getAcceptedCount(
        @Query("filter") filter:String
    ):Call<CountStat>

    @GET("/api/collections/proposal/records")
    fun getPendingCount(
        @Query("filter") filter:String
    ):Call<CountStat>
}