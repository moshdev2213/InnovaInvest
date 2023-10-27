package com.example.innova.EntityReq

import java.io.Serializable

data class UpdateUser(
    val telephone: String,
    val name: String,
    val description: String,
    val role: String,
): Serializable

