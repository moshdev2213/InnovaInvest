package com.example.innova.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.charmrides.RetrofitService.RetrofitService
import com.example.innova.Adapter.MyProjInvestAdapter
import com.example.innova.Adapter.ProjSkillAadpter
import com.example.innova.ApiService.ProjectService
import com.example.innova.DialogAlerts.ApiProgress
import com.example.innova.EntityRes.ProjTotalBudget
import com.example.innova.EntityRes.ProjectItem
import com.example.innova.EntityRes.Proposal
import com.example.innova.EntityRes.ProposalRes
import com.example.innova.EntityRes.UserRecord
import com.example.innova.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvestorProporsal : AppCompatActivity() {
    private lateinit var rvDisInvestForProf:RecyclerView
    private lateinit var myProjInvestAdapter:MyProjInvestAdapter
    private lateinit var imgGoBack:ImageView
    private lateinit var apiProgress: ApiProgress
    private var out: UserRecord? = null
    private var project: ProjectItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_investor_proporsal)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
            project = bundle.getSerializable("project") as ProjectItem
            initRecycler()
        }
    }
    private fun initRecycler(){
        rvDisInvestForProf = findViewById(R.id.rvDisInvestForProf)
        rvDisInvestForProf.layoutManager= LinearLayoutManager(this@InvestorProporsal)
        myProjInvestAdapter = MyProjInvestAdapter ({
                proposal: Proposal -> projCardClicked(proposal)
        },this@InvestorProporsal)
        rvDisInvestForProf.adapter = myProjInvestAdapter
        getRelevantInvestorForProj(project!!.id)

    }

    private fun projCardClicked(proposal: Proposal) {
        val bundle = Bundle()
        bundle.putSerializable("user", out)
        bundle.putSerializable("project", project)
        bundle.putSerializable("proposal", proposal)

        val intent = Intent(this@InvestorProporsal, InvestorDetail::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun getRelevantInvestorForProj(projectId: String) {
        apiProgress = ApiProgress(this@InvestorProporsal)
        apiProgress.startProgressLoader()
        val filterValue = "(forProject=\"${projectId}\")"
        val expand = "forProject"

        val retrofitService= RetrofitService()
        val getList =retrofitService.getRetrofit().create(ProjectService::class.java)
        val call: Call<ProposalRes> = getList.getRelevantInvestorForProj(expand,filterValue)

        call.enqueue(object : Callback<ProposalRes> {
            override fun onResponse(call: Call<ProposalRes>, response: Response<ProposalRes>) {
                if(response.isSuccessful){
                    if (response.body()!=null){
                        println(response.body())
                        val mealRes = response.body()
                        val mealItem = mealRes?.items
                        if (mealItem != null) {
                            myProjInvestAdapter.setList(mealItem)
                        }
                        apiProgress.dismissProgressLoader()
                    }else{
                        Toast.makeText(this@InvestorProporsal,"Empty Data", Toast.LENGTH_SHORT).show()
                        apiProgress.dismissProgressLoader()

                    }
                }else{
                    Toast.makeText(this@InvestorProporsal,"Invalid response", Toast.LENGTH_SHORT).show()
                    apiProgress.dismissProgressLoader()

                }
            }
            override fun onFailure(call: Call<ProposalRes>, t: Throwable) {
                Toast.makeText(this@InvestorProporsal,"Server Error", Toast.LENGTH_SHORT).show()
                apiProgress.dismissProgressLoader()

            }
        })
    }
}