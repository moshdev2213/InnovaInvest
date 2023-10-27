package com.example.charmrides.ApiService
import com.example.innova.EntityRes.UserRecord
import com.example.invest.EntityReq.AuthPassEmail
import com.example.invest.EntityReq.AuthSignUp
import com.example.invest.EntityRes.AuthSignUpRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/collections/investor/auth-with-password")
    fun getUserAuth(
        @Body authPassEmail: AuthPassEmail
    ): Call<UserRecord>

    @POST("/api/collections/investor/records")
    fun createUserAuth(
        @Body authSignUp: AuthSignUp
    ):Call<AuthSignUpRes>
}