package com.example.invest.EntityReq

import java.io.Serializable

data class UpdateUser(
    val telephone: String,
    val name: String,
    val description: String
): Serializable

