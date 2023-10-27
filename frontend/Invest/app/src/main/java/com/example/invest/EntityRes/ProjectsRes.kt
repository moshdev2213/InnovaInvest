package com.example.invest.EntityRes

import java.io.Serializable

data class ProjectsRes(
    val page: Int,
    val perPage: Int,
    val totalPages: Int,
    val totalItems: Int,
    val items: List<ProjectItem>
):Serializable
data class ProjectItem(
    val id: String,
    val collectionId: String,
    val collectionName: String,
    val created: String,
    val updated: String,
    val category: String,
    val title: String,
    val email: String,
    val description: String,
    val skillsRequired: String,
    val budget: Int,
    val timeLine: String
):Serializable
