package com.example.innova.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.charmrides.RetrofitService.RetrofitService
import com.example.charmrides.Validation.ValidationResult
import com.example.innova.ApiService.ProjectService
import com.example.innova.ApiService.UserService
import com.example.innova.DialogAlerts.ApiProgress
import com.example.innova.EntityReq.UpdateUser
import com.example.innova.EntityRes.CountStat
import com.example.innova.EntityRes.ProposalRes
import com.example.innova.EntityRes.Record
import com.example.innova.EntityRes.UserRecord
import com.example.innova.FormData.AddProjForm
import com.example.innova.FormData.EditUserForm
import com.example.innova.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfile : AppCompatActivity() {
    private var out: UserRecord? = null
    private var count: Int = 0
    private lateinit var apiProgress: ApiProgress
    private lateinit var cvDoneEditProfile: CardView
    private lateinit var cvGoBackEditProfile: CardView
    private lateinit var etDesOfUser: EditText
    private lateinit var etNameOfUser: EditText
    private lateinit var etTelOfUser: EditText
    private lateinit var etEmailOfUser: EditText
    private lateinit var tvPendingCount: TextView
    private lateinit var tvAcceptedCount: TextView
    private lateinit var imbBackBtnToProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
        }
        imbBackBtnToProfile = findViewById(R.id.imbBackBtnToProfile)
        tvAcceptedCount = findViewById(R.id.tvAcceptedCount)
        tvPendingCount = findViewById(R.id.tvPendingCount)
        etEmailOfUser = findViewById(R.id.etEmailOfUser)
        etTelOfUser = findViewById(R.id.etTelOfUser)
        etNameOfUser = findViewById(R.id.etNameOfUser)
        etDesOfUser = findViewById(R.id.etDesOfUser)
        cvGoBackEditProfile = findViewById(R.id.cvGoBackEditProfile)
        cvDoneEditProfile = findViewById(R.id.cvDoneEditProfile)

        imbBackBtnToProfile.setOnClickListener {
            finish()
        }
        cvGoBackEditProfile.setOnClickListener {
            finish()
        }
        fetchUserData()
        cvDoneEditProfile.setOnClickListener {
            updateUserData(etTelOfUser.text.toString(),etNameOfUser.text.toString(),etDesOfUser.text.toString())
        }
    }
    private fun fetchUserData(){
        apiProgress = ApiProgress(this@EditProfile)
        apiProgress.startProgressLoader()

        val retrofitService= RetrofitService()
        val getList =retrofitService.getRetrofit().create(UserService::class.java)
        if(out?.record?.id?.isNotEmpty() == true){
            val call: Call<Record> = getList.getUserDetails(out?.record?.id!!)

            call.enqueue(object : Callback<Record> {
                override fun onResponse(call: Call<Record>, response: Response<Record>) {
                    println(response.code())
                    if(response.isSuccessful){
                        if (response.body()!=null){
                            fetchGetPendingCount()
                            fetchGetAcceptedCount()
                            populateEditText(response.body()!!)
                            apiProgress.dismissProgressLoader()
                        }else{
                            Toast.makeText(this@EditProfile,"Empty Data", Toast.LENGTH_SHORT).show()
                            apiProgress.dismissProgressLoader()
                        }
                    }else{
                        Toast.makeText(this@EditProfile,"Invalid response", Toast.LENGTH_SHORT).show()
                        apiProgress.dismissProgressLoader()
                    }
                }
                override fun onFailure(call: Call<Record>, t: Throwable) {
                    Toast.makeText(this@EditProfile,"Server Error", Toast.LENGTH_SHORT).show()
                    apiProgress.dismissProgressLoader()
                }
            })
        }
    }
    private fun fetchGetPendingCount(){

        val filterValue = "(email=\"${out?.record?.email}\" && status='rejected')"

        val retrofitService= RetrofitService()
        val getList =retrofitService.getRetrofit().create(UserService::class.java)
        if(out?.record?.id?.isNotEmpty() == true){
            val call: Call<CountStat> = getList.getPendingCount(filterValue)

            call.enqueue(object : Callback<CountStat> {
                override fun onResponse(call: Call<CountStat>, response: Response<CountStat>) {
                    println(response.code())
                    if(response.isSuccessful){
                        if (response.body()!=null){
                            tvPendingCount.text = response.body()!!.totalItems.toString()
                        }else{
                            Toast.makeText(this@EditProfile,"Empty Data", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@EditProfile,"Invalid response", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<CountStat>, t: Throwable) {
                    Toast.makeText(this@EditProfile,"Server Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    private fun fetchGetAcceptedCount(){
        val filterValue = "(email=\"${out?.record?.email}\" && status='approved')"
        val retrofitService= RetrofitService()
        val getList =retrofitService.getRetrofit().create(UserService::class.java)
        if(out?.record?.id?.isNotEmpty() == true){
            val call: Call<CountStat> = getList.getAcceptedCount(filterValue)

            call.enqueue(object : Callback<CountStat> {
                override fun onResponse(call: Call<CountStat>, response: Response<CountStat>) {
                    println(response.code())
                    if(response.isSuccessful){
                        if (response.body()!=null){
                            tvAcceptedCount.text = response.body()!!.totalItems.toString()
                        }else{
                            Toast.makeText(this@EditProfile,"Empty Data", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@EditProfile,"Invalid response", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<CountStat>, t: Throwable) {
                    Toast.makeText(this@EditProfile,"Server Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    private fun populateEditText(record: Record){
        etDesOfUser.setText(record.description)
        etEmailOfUser.setText(record.email)
        etTelOfUser.setText(record.telephone.toString())
        etNameOfUser.setText(record.name)
    }
    private fun updateUserData(tel:String,name:String,description:String){
        validateForm(name, tel, description)
        if(count==3){
            apiProgress = ApiProgress(this@EditProfile)
            apiProgress.startProgressLoader()

            val retrofitService= RetrofitService()
            val getList =retrofitService.getRetrofit().create(UserService::class.java)
            if(out?.record?.id?.isNotEmpty() == true){
                val call: Call<Record> = getList.updateUserData(
                    out?.record?.id!!,
                    UpdateUser(
                        tel,name,description,"entrepreneur"
                    )
                )
                println("Request URL: ${call.request().body().toString()} -> ${out?.record?.id}") // Log the URL
                println("Request Body: ${call.request().body()}") // Log the request body (if present)
                call.enqueue(object : Callback<Record> {
                    override fun onResponse(call: Call<Record>, response: Response<Record>) {
                        println(response.body())
                        if(response.isSuccessful){
                            if (response.body()!=null){
                                apiProgress.dismissProgressLoader()
                                finish()
                            }else{
                                Toast.makeText(this@EditProfile,"Empty Data", Toast.LENGTH_SHORT).show()
                                apiProgress.dismissProgressLoader()
                            }
                        }else{
                            Toast.makeText(this@EditProfile,"Invalid response", Toast.LENGTH_SHORT).show()
                            apiProgress.dismissProgressLoader()
                        }
                    }
                    override fun onFailure(call: Call<Record>, t: Throwable) {
                        Toast.makeText(this@EditProfile,"Server Error", Toast.LENGTH_SHORT).show()
                        apiProgress.dismissProgressLoader()
                    }
                })
            }
            count=0;
        }
        count=0;
    }
    private fun validateForm(name:String,tel:String,description:String){
        val addProjForm = EditUserForm(name, tel, description)

        val validateName =addProjForm.validateName()
        val validateTel =addProjForm.validateTelephone()
        val validateDescription =addProjForm.validateDescription()

        when(validateName){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etNameOfUser.error =validateName.errorMsg
            }
            is ValidationResult.Empty ->{
                etNameOfUser.error =validateName.errorMsg
            }
        }

        when(validateTel){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etTelOfUser.error =validateTel.errorMsg

            }
            is ValidationResult.Empty ->{
                etTelOfUser.error =validateTel.errorMsg

            }
        }
        when(validateDescription){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etDesOfUser.error =validateDescription.errorMsg
            }
            is ValidationResult.Empty ->{
                etDesOfUser.error =validateDescription.errorMsg
            }
        }
    }

}