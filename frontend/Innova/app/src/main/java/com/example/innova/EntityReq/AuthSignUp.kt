package com.example.innova.EntityReq

import java.io.Serializable

data class AuthSignUp(
    val telephone:String,
    val email: String,
    val password:String,
    val passwordConfirm:String,
    val name:String,
): Serializable
