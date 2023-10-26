package com.example.charmrides.ApiService

import com.example.charmrides.Entity.AuthPassEmail
import com.example.charmrides.Entity.AuthSignUp
import com.example.charmrides.EntityRes.AuthSignUpRes
import com.example.charmrides.EntityRes.UserRecord
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/collections/users/auth-with-password")
    fun getUserAuth(
        @Body authPassEmail: AuthPassEmail
    ): Call<UserRecord>

    @POST("/api/collections/users/records")
    fun createUserAuth(
        @Body authSignUp: AuthSignUp
    ):Call<AuthSignUpRes>
}