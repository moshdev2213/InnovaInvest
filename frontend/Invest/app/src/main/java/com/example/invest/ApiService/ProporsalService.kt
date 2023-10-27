package com.example.invest.ApiService

import com.example.invest.EntityReq.AddProposalReq
import com.example.invest.EntityRes.ProjectsRes
import com.example.invest.EntityRes.Proposal
import com.example.invest.EntityRes.ProposalRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProporsalService {
    @POST("/api/collections/proposal/records")
    fun createNewProporsal(
        @Body addProjectReq: AddProposalReq
    ): Call<AddProposalReq>

    @GET("/api/collections/proposal/records")
    fun getAllPropoOfUser(
        @Query("filter")filter : String,
        @Query("expand")expand : String
    ): Call<ProposalRes>
}