package com.example.innova.ApiService

import com.example.innova.Activity.AddProject
import com.example.innova.EntityReq.AddProjectReq
import com.example.innova.EntityRes.ProjTotalBudget
import com.example.innova.EntityRes.ProjectsRes
import com.example.innova.EntityRes.Proposal
import com.example.innova.EntityRes.ProposalRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProjectService {
    @GET("/api/collections/project/records")
    fun getProjNotUser(
        @Query("filter")filter : String
    ): Call<ProjectsRes>

    @GET("/api/collections/proposal/records")
    fun getProjTotBudById(
        @Query("expand") expand: String,
        @Query("fields") fields: String
//        @Query("filter") filter: String
        ): Call<ProjTotalBudget>

    @POST("/api/collections/project/records")
    fun createNewProject(
        @Body addProjectReq: AddProjectReq
    ):Call<AddProjectReq>

    @GET("/api/collections/proposal/records")
    fun getRelevantInvestorForProj(
        @Query("expand") expand: String,
        @Query("filter") filter: String
    ):Call<ProposalRes>

    @GET("/api/collections/proposal/records")
    fun getOtherProjOfUser(
        @Query("expand") expand: String,
        @Query("fields") fields: String,
        @Query("filter") filter: String
    ):Call<ProposalRes>
}