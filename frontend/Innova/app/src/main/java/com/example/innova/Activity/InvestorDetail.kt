package com.example.innova.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.charmrides.RetrofitService.RetrofitService
import com.example.innova.Adapter.PrevProjAdapter
import com.example.innova.Adapter.ProjSkillAadpter
import com.example.innova.ApiService.ProjectService
import com.example.innova.DialogAlerts.ApiProgress
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
}