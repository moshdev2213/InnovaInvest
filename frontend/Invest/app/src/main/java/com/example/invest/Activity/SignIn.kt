package com.example.invest.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.charmrides.ApiService.AuthService
import com.example.charmrides.FormData.UserLoginForm
import com.example.charmrides.RetrofitService.RetrofitService
import com.example.charmrides.Validation.ValidationResult
import com.example.fitme.DialogAlerts.ProgressLoader
import com.example.innova.EntityRes.UserRecord
import com.example.invest.EntityReq.AuthPassEmail
import com.example.invest.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignIn : AppCompatActivity() {
    private lateinit var tvredirectToSignUp: TextView
    private lateinit var cvSignInBtn: CardView
    private lateinit var etSignInPassword: EditText
    private lateinit var etSignInEmail: EditText
    private lateinit var progressLoader: ProgressLoader
    private  var count:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        tvredirectToSignUp = findViewById(R.id.tvredirectToSignUp)
        cvSignInBtn = findViewById(R.id.cvSignInBtn)
        etSignInPassword = findViewById(R.id.etSignInPassword)
        etSignInEmail = findViewById(R.id.etSignInEmail)

        tvredirectToSignUp.setOnClickListener {
            startActivity(Intent(this@SignIn,SignUp::class.java))
        }
        cvSignInBtn.setOnClickListener {
            authEmail(etSignInEmail.text.toString(),etSignInPassword.text.toString())
        }
    }
    private fun authEmail(email: String, password: String){
        validateForm(email,password)

        if(count==2){
            progressLoader = ProgressLoader(
                this@SignIn,"Verifying Login","Please Wait"
            )
            progressLoader.startProgressLoader()
            val retrofitService = RetrofitService()
            val authService: AuthService = retrofitService.getRetrofit().create(AuthService::class.java)

            val call: Call<UserRecord> = authService.getUserAuth(
                AuthPassEmail(email,password)
            )
            call.enqueue(object : Callback<UserRecord> {
                override fun onResponse(call: Call<UserRecord>, response: Response<UserRecord>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            val intent = Intent(this@SignIn, Home::class.java)
                            intent.putExtra("user", user) // Assuming "patient" is Parcelable or Serializable
                            startActivity(intent)
                            progressLoader.dismissProgressLoader()
                            finish()
                        }
                    } else {
                        Toast.makeText(this@SignIn, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        progressLoader.dismissProgressLoader()
                    }
                }

                override fun onFailure(call: Call<UserRecord>, t: Throwable) {
                    Toast.makeText(this@SignIn, "Server Error", Toast.LENGTH_SHORT).show()
                    progressLoader.dismissProgressLoader()
                    println(t.message)
                }
            })
            count=0;
        }
        count=0;

    }
    private fun validateForm(email:String,password:String){

        val userLoginForm = UserLoginForm(
            email,
            password
        )
        val emailValidation =userLoginForm.validateEmail()
        val passwordValidation =userLoginForm.validatePassword()

        when(emailValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etSignInEmail.error =emailValidation.errorMsg
            }
            is ValidationResult.Empty ->{
                etSignInEmail.error =emailValidation.errorMsg
            }
        }

        when(passwordValidation){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etSignInPassword.error =passwordValidation.errorMsg

            }
            is ValidationResult.Empty ->{
                etSignInPassword.error =passwordValidation.errorMsg

            }
        }
    }
}