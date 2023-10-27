package com.example.innova.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.charmrides.RetrofitService.RetrofitService
import com.example.innova.Adapter.PrevProjAdapter
import com.example.innova.Adapter.ProjSkillAadpter
import com.example.innova.ApiService.ProjectService
import com.example.innova.DialogAlerts.ApiProgress
import com.example.innova.EntityReq.UpdateStatus
import com.example.innova.EntityRes.ProjectItem
import com.example.innova.EntityRes.Proposal
import com.example.innova.EntityRes.ProposalRes
import com.example.innova.EntityRes.UserRecord
import com.example.innova.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvestorDetail : AppCompatActivity() {
    private var out: UserRecord? = null
    private var project: ProjectItem? = null
    private var proposal: Proposal? = null
    private lateinit var apiProgress: ApiProgress
    private lateinit var projSkillAadpter: PrevProjAdapter
    private lateinit var rvPrevInvest: RecyclerView

    private lateinit var imgBackBtn: ImageView
    private lateinit var tvProjPropoNameDetail: TextView
    private lateinit var tvProjPropoTypeDetail: TextView
    private lateinit var tvProjPropoFromDetail: TextView
    private lateinit var tvProjPropoToDetail: TextView
    private lateinit var tvMealDescLong: TextView
    private lateinit var cvBackBtn: CardView
    private lateinit var cvBuyBtn: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_investor_detail)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
            project = bundle.getSerializable("project") as ProjectItem
            proposal = bundle.getSerializable("proposal") as Proposal

            initRecycler()
        }

        imgBackBtn = findViewById(R.id.imgBackBtn)
        tvProjPropoNameDetail = findViewById(R.id.tvProjPropoNameDetail)
        tvProjPropoTypeDetail = findViewById(R.id.tvProjPropoTypeDetail)
        tvProjPropoFromDetail = findViewById(R.id.tvProjPropoFromDetail)
        tvProjPropoToDetail = findViewById(R.id.tvProjPropoToDetail)
        tvMealDescLong = findViewById(R.id.tvMealDescLong)
        cvBackBtn = findViewById(R.id.cvBackBtn)
        cvBuyBtn = findViewById(R.id.cvBuyBtn)

        imgBackBtn.setOnClickListener {
            finish()
        }
        cvBackBtn.setOnClickListener {
            //implement the rject update here
            statusToRejected()
        }
        cvBuyBtn.setOnClickListener {
            //implement the pnding status to approval
            statusToAccepted()
        }
        tvProjPropoNameDetail.text = proposal?.email
        tvProjPropoTypeDetail.text = "Tel : ${proposal?.tel.toString()}"
        tvProjPropoFromDetail.text = "Rs ${proposal?.budget.toString()}"
        tvProjPropoToDetail.text = proposal?.expectedProfit.toString()
        tvMealDescLong.text = proposal?.comments?.capitalize()
    }

    private fun initRecycler(){
        rvPrevInvest = findViewById(R.id.rvPrevInvest)
        rvPrevInvest.layoutManager= LinearLayoutManager(this@InvestorDetail)
        projSkillAadpter = PrevProjAdapter ()
        rvPrevInvest.adapter = projSkillAadpter

        getOtherProjOfUser()
    }
    private fun getOtherProjOfUser(){
        apiProgress = ApiProgress(this@InvestorDetail)
        apiProgress.startProgressLoader()

        val filterValue = "(forProject!=\"${project?.id}\" && email=\"${proposal?.email}\")"
        val expand = "forProject"
        val fields = "expand.forProject.title"

        val retrofitService= RetrofitService()
        val getList =retrofitService.getRetrofit().create(ProjectService::class.java)
        val call: Call<ProposalRes> = getList.getOtherProjOfUser(expand,fields,filterValue)

        call.enqueue(object : Callback<ProposalRes> {
            override fun onResponse(call: Call<ProposalRes>, response: Response<ProposalRes>) {
                if(response.isSuccessful){
                    if (response.body()!=null){
                        println(response.body())
                        val mealRes = response.body()
                        val mealItem = mealRes?.items
                        println("forProject!=${project?.id} && email=${out?.record?.email}")
                        if (mealItem != null) {
                            if (mealItem.isNotEmpty()) {
                                projSkillAadpter.setList(mealItem)
                            }
                        }
                        apiProgress.dismissProgressLoader()
                    }else{
                        Toast.makeText(this@InvestorDetail,"Empty Data", Toast.LENGTH_SHORT).show()
                        apiProgress.dismissProgressLoader()
                    }
                }else{
                    Toast.makeText(this@InvestorDetail,"Invalid response", Toast.LENGTH_SHORT).show()
                    apiProgress.dismissProgressLoader()
                }
            }
            override fun onFailure(call: Call<ProposalRes>, t: Throwable) {
                Toast.makeText(this@InvestorDetail,"Server Error", Toast.LENGTH_SHORT).show()
                apiProgress.dismissProgressLoader()

            }
        })
    }
    private fun statusToAccepted(){
        apiProgress = ApiProgress(this@InvestorDetail)
        apiProgress.startProgressLoader()

        val retrofitService= RetrofitService()
        val getList =retrofitService.getRetrofit().create(ProjectService::class.java)

        if(proposal!=null){
            val call: Call<Proposal> = getList.UpdateStatusToAccepted(
                proposal!!.id,
                UpdateStatus(proposal!!.email, proposal!!.forProject,"approved", proposal!!.tel)
            )
            call.enqueue(object : Callback<Proposal> {
                override fun onResponse(call: Call<Proposal>, response: Response<Proposal>) {
                    if(response.isSuccessful){
                        if (response.body()!=null){
                            val bundle = Bundle()
                            bundle.putSerializable("user", out)
                            val intent = Intent(this@InvestorDetail, Success::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                            apiProgress.dismissProgressLoader()
                            finish()
                        }else{
                            Toast.makeText(this@InvestorDetail,"Empty Data", Toast.LENGTH_SHORT).show()
                            apiProgress.dismissProgressLoader()
                        }
                    }else{
                        Toast.makeText(this@InvestorDetail,"Invalid response", Toast.LENGTH_SHORT).show()
                        apiProgress.dismissProgressLoader()
                    }
                }
                override fun onFailure(call: Call<Proposal>, t: Throwable) {
                    Toast.makeText(this@InvestorDetail,"Server Error", Toast.LENGTH_SHORT).show()
                    apiProgress.dismissProgressLoader()

                }
            })
        }
    }
    private fun statusToRejected() {
        apiProgress = ApiProgress(this@InvestorDetail)
        apiProgress.startProgressLoader()

        val retrofitService= RetrofitService()
        val getList =retrofitService.getRetrofit().create(ProjectService::class.java)

        if(proposal!=null){
            val call: Call<Proposal> = getList.UpdateStatusToAccepted(
                proposal!!.id,
                UpdateStatus(proposal!!.email, proposal!!.forProject,"rejected", proposal!!.tel)
            )
            call.enqueue(object : Callback<Proposal> {
                override fun onResponse(call: Call<Proposal>, response: Response<Proposal>) {
                    if(response.isSuccessful){
                        if (response.body()!=null){
                            apiProgress.dismissProgressLoader()

                            val bundle = Bundle()
                            bundle.putSerializable("user", out)
                            bundle.putSerializable("project", project)
                            val intent = Intent(this@InvestorDetail, InvestorProporsal::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this@InvestorDetail,"Empty Data", Toast.LENGTH_SHORT).show()
                            apiProgress.dismissProgressLoader()
                        }
                    }else{
                        Toast.makeText(this@InvestorDetail,"Invalid response", Toast.LENGTH_SHORT).show()
                        apiProgress.dismissProgressLoader()
                    }
                }
                override fun onFailure(call: Call<Proposal>, t: Throwable) {
                    Toast.makeText(this@InvestorDetail,"Server Error", Toast.LENGTH_SHORT).show()
                    apiProgress.dismissProgressLoader()

                }
            })
        }
    }
}