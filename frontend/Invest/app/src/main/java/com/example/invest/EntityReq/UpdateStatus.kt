package com.example.invest.EntityReq

import java.io.Serializable

data class UpdateStatus(
    val email: String,
    val forProject: String,
    val status: String,
    val tel: Long
): Serializable
