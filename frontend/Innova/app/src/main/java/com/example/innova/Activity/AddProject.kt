package com.example.innova.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.charmrides.ApiService.AuthService
import com.example.charmrides.FormData.UserSignUp
import com.example.charmrides.RetrofitService.RetrofitService
import com.example.charmrides.Validation.ValidationResult
import com.example.fitme.DialogAlerts.ProgressLoader
import com.example.innova.ApiService.ProjectService
import com.example.innova.DialogAlerts.ApiProgress
import com.example.innova.EntityReq.AddProjectReq
import com.example.innova.EntityReq.AuthSignUp
import com.example.innova.EntityRes.AuthSignUpRes
import com.example.innova.EntityRes.UserRecord
import com.example.innova.FormData.AddProjForm
import com.example.innova.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProject : AppCompatActivity() {
    private lateinit var cvAddProj:CardView
    private lateinit var cvCancelAddProj:CardView
    private lateinit var etProjDesc:EditText
    private lateinit var etBudgetProj:EditText
    private lateinit var etSkillsProjRequired:EditText
    private lateinit var etProjTitle:EditText
    private lateinit var spinnerProjCatSelect:Spinner
    private lateinit var spinnerTimelineProj:Spinner
    private lateinit var imbBackBtn:ImageView
    private var count=0
    private lateinit  var apiProgress:ApiProgress
    private var out: UserRecord? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_project)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
        }

        spinnerTimelineProj = findViewById(R.id.spinnerTimelineProj)
        spinnerProjCatSelect = findViewById(R.id.spinnerProjCatSelect)
        etProjTitle = findViewById(R.id.etProjTitle)
        etSkillsProjRequired = findViewById(R.id.etSkillsProjRequired)
        etBudgetProj = findViewById(R.id.etBudgetProj)
        etProjDesc = findViewById(R.id.etProjDesc)
        cvCancelAddProj = findViewById(R.id.cvCancelAddProj)
        cvAddProj = findViewById(R.id.cvAddProj)
        imbBackBtn = findViewById(R.id.imbBackBtn)

        cvAddProj.setOnClickListener {
            insertData(etProjTitle.text.toString(),etSkillsProjRequired.text.toString(),etBudgetProj.text.toString(),etProjDesc.text.toString(),
                spinnerTimelineProj.selectedItem.toString(),spinnerProjCatSelect.selectedItem.toString())
        }
        cvCancelAddProj.setOnClickListener {
            finish()
        }
        imbBackBtn.setOnClickListener {
            finish()
        }
    }
    private fun insertData(projTitle:String,skills:String,budget:String,description:String,timeLine:String,category:String){
        validateForm(projTitle, skills, budget, description)
        if(count==4){
            apiProgress = ApiProgress(
                this@AddProject
            )
            apiProgress.startProgressLoader()
            val retrofitService = RetrofitService()
            val projService: ProjectService = retrofitService.getRetrofit().create(ProjectService::class.java)

            val call: Call<AddProjectReq> = projService.createNewProject(
                AddProjectReq("","","",category,projTitle,
                    out?.record?.email ?:"",description,skills,budget.toInt(),timeLine)
            )
            call.enqueue(object : Callback<AddProjectReq> {
                override fun onResponse(call: Call<AddProjectReq>, response: Response<AddProjectReq>) {
                    println(response.body())
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            val bundle = Bundle()
                            bundle.putSerializable("user", out)

                            val intent = Intent(this@AddProject, Success::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Toast.makeText(this@AddProject, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        apiProgress.dismissProgressLoader()
                    }
                }

                override fun onFailure(call: Call<AddProjectReq>, t: Throwable) {
                    Toast.makeText(this@AddProject, "Server Error", Toast.LENGTH_SHORT).show()
                    apiProgress.dismissProgressLoader()
                }
            })
            count=0;
        }
        count=0;
    }
    private fun validateForm(projTitle:String,skills:String,budget:String,description:String){
        val addProjForm = AddProjForm(projTitle, skills, budget, description)

        val validateProjTitle =addProjForm.validateProjTitle()
        val validateProjSkills =addProjForm.validateProjSkills()
        val validateBudget =addProjForm.validateBudget()
        val validateProjDes =addProjForm.validateProjDes()

        when(validateProjTitle){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etProjTitle.error =validateProjTitle.errorMsg
            }
            is ValidationResult.Empty ->{
                etProjTitle.error =validateProjTitle.errorMsg
            }
        }

        when(validateProjSkills){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etSkillsProjRequired.error =validateProjSkills.errorMsg

            }
            is ValidationResult.Empty ->{
                etSkillsProjRequired.error =validateProjSkills.errorMsg

            }
        }
        when(validateBudget){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etBudgetProj.error =validateBudget.errorMsg
            }
            is ValidationResult.Empty ->{
                etBudgetProj.error =validateBudget.errorMsg

            }
        }
        when(validateProjDes){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                etProjDesc.error =validateProjDes.errorMsg

            }
            is ValidationResult.Empty ->{
                etProjDesc.error =validateProjDes.errorMsg

            }
        }
    }
}