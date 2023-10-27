package com.example.invest.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.charmrides.RetrofitService.RetrofitService
import com.example.charmrides.Validation.ValidationResult
import com.example.innova.DialogAlerts.ApiProgress
import com.example.innova.EntityRes.UserRecord
import com.example.innova.FormData.AddProjForm
import com.example.invest.ApiService.ProporsalService
import com.example.invest.EntityReq.AddProposalReq
import com.example.invest.EntityRes.ProjectItem
import com.example.invest.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendProporsal : AppCompatActivity() {
    private lateinit var cvApplyPropo:CardView
    private lateinit var imbBackBtnPropo:ImageView
    private lateinit var sendPropoComment:EditText
    private lateinit var sendPropoBudget:EditText
    private lateinit var sndPropoTel:EditText
    private lateinit var sendPropoEmail:EditText
    private lateinit var spinneSelectProfit: Spinner
    private var count:Int=0
    private lateinit  var apiProgress:ApiProgress
    private var out: UserRecord? = null
    private var project: ProjectItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_proposal)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
            project = bundle.getSerializable("project") as ProjectItem
        }

        spinneSelectProfit = findViewById(R.id.spinneSelectProfit)
        sendPropoEmail = findViewById(R.id.sendPropoEmail)
        sndPropoTel = findViewById(R.id.sndPropoTel)
        sendPropoBudget = findViewById(R.id.sendPropoBudget)
        sendPropoComment = findViewById(R.id.sendPropoComment)
        cvApplyPropo = findViewById(R.id.cvApplyPropo)
        imbBackBtnPropo = findViewById(R.id.imbBackBtnPropo)

        sendPropoEmail.setText(out?.record?.email)

        imbBackBtnPropo.setOnClickListener {
            finish()
        }
        cvApplyPropo.setOnClickListener {
            insertData(sndPropoTel.text.toString(),
                sendPropoBudget.text.toString(),
                sendPropoComment.text.toString(),
                spinneSelectProfit.selectedItem.toString())
        }
    }
    private fun insertData(tel:String,budget:String,description:String,profit:String){
        validateForm(tel, budget, description)
        if(count==3){
            apiProgress = ApiProgress(
                this@SendProporsal
            )
            apiProgress.startProgressLoader()
            val retrofitService = RetrofitService()
            val proporsalService: ProporsalService = retrofitService.getRetrofit().create(ProporsalService::class.java)
            if(out?.record?.email != null && project?.id?.isNotEmpty() == true){
                val call: Call<AddProposalReq> = proporsalService.createNewProporsal(
                    AddProposalReq("",
                        out?.record?.email!!,
                        tel,
                        budget.toInt(),
                        description,
                        profit.toInt(),
                        project?.id!!,"pending"
                    )
                )
                call.enqueue(object : Callback<AddProposalReq> {
                    override fun onResponse(call: Call<AddProposalReq>, response: Response<AddProposalReq>) {
                        println(response.body())
                        if (response.isSuccessful) {
                            val user = response.body()
                            if (user != null) {
                                val bundle = Bundle()
                                bundle.putSerializable("user", out)

                                val intent = Intent(this@SendProporsal, Success::class.java)
                                intent.putExtras(bundle)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(this@SendProporsal, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                            apiProgress.dismissProgressLoader()
                        }
                    }

                    override fun onFailure(call: Call<AddProposalReq>, t: Throwable) {
                        Toast.makeText(this@SendProporsal, "Server Error", Toast.LENGTH_SHORT).show()
                        apiProgress.dismissProgressLoader()
                    }
                })
                count=0;
            }
        }
        count=0;
    }
    private fun validateForm(tel:String,budget:String,description:String){
        val addProjForm = AddProjForm(tel,budget, description)

        val validateTel =addProjForm.validateTelephone()
        val validateBudget =addProjForm.validateBudget()
        val validateProjDes =addProjForm.validateProjDes()

        when(validateTel){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                sndPropoTel.error =validateTel.errorMsg
            }
            is ValidationResult.Empty ->{
                sndPropoTel.error =validateTel.errorMsg
            }
        }

        when(validateBudget){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                sendPropoBudget.error =validateBudget.errorMsg

            }
            is ValidationResult.Empty ->{
                sendPropoBudget.error =validateBudget.errorMsg

            }
        }

        when(validateProjDes){
            is ValidationResult.Valid ->{ count ++ }
            is ValidationResult.Invalid ->{
                sendPropoComment.error =validateProjDes.errorMsg

            }
            is ValidationResult.Empty ->{
                sendPropoComment.error =validateProjDes.errorMsg

            }
        }
    }
}