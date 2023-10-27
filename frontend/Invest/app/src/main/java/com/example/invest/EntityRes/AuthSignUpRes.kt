package com.example.invest.EntityRes

import java.io.Serializable


data class AuthSignUpRes(
    val telephone:String,
    val email: String,
    val name:String,
):Serializable
