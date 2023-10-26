package com.example.innova.ApiService

import com.example.innova.EntityRes.ProjectsRes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProjectService {
    @GET("/api/collections/project/records")
    fun getProjNotUser(
        @Query("filter")filter : String
    ): Call<ProjectsRes>
}