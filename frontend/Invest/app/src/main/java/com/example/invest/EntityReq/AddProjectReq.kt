package com.example.invest.EntityReq

import java.io.Serializable

data class AddProjectReq(
    val id: String,
    val created: String,
    val updated: String,
    val category: String,
    val title: String,
    val email: String,
    val description: String,
    val skillsRequired: String,
    val budget: Int,
    val timeLine: String
): Serializable