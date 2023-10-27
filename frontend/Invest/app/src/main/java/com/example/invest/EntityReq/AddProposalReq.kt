package com.example.invest.EntityReq

import java.io.Serializable

data class AddProposalReq(
    val id: String,
    val email: String,
    val tel: String,
    val budget: Int,
    val comments: String,
    val expectedProfit: Int,
    val forProject: String,
    val status: String
):Serializable