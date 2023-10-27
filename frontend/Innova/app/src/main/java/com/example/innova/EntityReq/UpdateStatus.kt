package com.example.innova.EntityReq

import com.example.innova.EntityRes.Expand
import java.io.Serializable

data class UpdateStatus(
    val email: String,
    val forProject: String,
    val status: String,
    val tel: Long
): Serializable
