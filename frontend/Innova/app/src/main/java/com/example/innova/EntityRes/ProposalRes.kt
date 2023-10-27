package com.example.innova.EntityRes

import java.io.Serializable

data class ProposalRes(
    val items: List<Proposal>
):Serializable

data class Proposal(
    val budget: Int,
    val comments: String,
    val email: String,
    val expand: Expand,
    val expectedProfit: Int,
    val forProject: String,
    val id: String,
    val status: String,
    val tel: Long,
    val updated: String,
    val created: String,
):Serializable

data class Expand(
    val forProject: ForProject
):Serializable

data class ForProject(
    val budget: Int,
    val category: String,
    val description: String,
    val email: String,
    val id: String,
    val skillsRequired: String,
    val timeLine: String,
    val title: String
):Serializable
