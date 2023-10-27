package com.example.invest.ApiService

import com.example.invest.EntityReq.AddProjectReq
import com.example.invest.EntityReq.UpdateStatus
import com.example.invest.EntityRes.ProjTotalBudget
import com.example.invest.EntityRes.ProjectsRes
import com.example.invest.EntityRes.Proposal
import com.example.invest.EntityRes.ProposalRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectService {
    @GET("/api/collections/project/records")
    fun getProjNotUser(
        @Query("filter")filter : String
    ): Call<ProjectsRes>

    @GET("/api/collections/project/records")
    fun getProjAllUser(
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

    @PATCH("/api/collections/proposal/records/{id}")
    fun UpdateStatusToAccepted(
        @Path("id") id:String,
        @Body updateStatus: UpdateStatus
    ):Call<Proposal>
}