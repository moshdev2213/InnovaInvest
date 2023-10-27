package com.example.invest.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.charmrides.RetrofitService.RetrofitService
import com.example.innova.Adapter.ProjectsAdapter
import com.example.innova.DialogAlerts.ApiProgress
import com.example.innova.EntityRes.UserRecord
import com.example.invest.Adapter.ProporsalAdapter
import com.example.invest.ApiService.ProjectService
import com.example.invest.ApiService.ProporsalService
import com.example.invest.EntityRes.ProjTotalBudget
import com.example.invest.EntityRes.ProjectItem
import com.example.invest.EntityRes.ProjectsRes
import com.example.invest.EntityRes.Proposal
import com.example.invest.EntityRes.ProposalRes
import com.example.invest.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProporsals : AppCompatActivity() {
    private var out: UserRecord? = null
    private lateinit var rvMyProProporsals: RecyclerView
    private lateinit var proporsalAdapter: ProporsalAdapter
    private lateinit var apiProgress: ApiProgress
    private lateinit var imgBackToProfileFromMy: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_proporsals)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
        }

        rvMyProProporsals = findViewById(R.id.rvMyProProporsals)
        imgBackToProfileFromMy = findViewById(R.id.imgBackToProfileFromMy)

        imgBackToProfileFromMy.setOnClickListener {
            finish()
        }

        initRecycler()
    }
    private fun fetchDetails() {
        apiProgress = ApiProgress(this@MyProporsals)
        apiProgress.startProgressLoader()

        val emailToFilter: String = out?.record?.email ?: ""
        val filterValue = "(email=\"$emailToFilter\")"
        val expand = "forProject"

        val retrofitService= RetrofitService()
        val getList =retrofitService.getRetrofit().create(ProporsalService::class.java)
        val call: Call<ProposalRes> = getList.getAllPropoOfUser(filterValue,expand)

        call.enqueue(object : Callback<ProposalRes> {
            override fun onResponse(call: Call<ProposalRes>, response: Response<ProposalRes>) {
                if(response.isSuccessful){
                    if (response.body()!=null){
                        println(response.body())
                        val mealRes = response.body()
                        val propList  = mealRes?.items
                        proporsalAdapter.setList(propList!!)
                        rvMyProProporsals.visibility= View.VISIBLE
                        apiProgress.dismissProgressLoader()
                    }else{
                        Toast.makeText(this@MyProporsals,"Empty Data", Toast.LENGTH_SHORT).show()
                        apiProgress.dismissProgressLoader()
                    }

                }else{
                    Toast.makeText(this@MyProporsals,"Invalid response", Toast.LENGTH_SHORT).show()
                    apiProgress.dismissProgressLoader()
                }
            }
            override fun onFailure(call: Call<ProposalRes>, t: Throwable) {
                Toast.makeText(this@MyProporsals,"Server Error", Toast.LENGTH_SHORT).show()
                apiProgress.dismissProgressLoader()
            }
        })
    }
    private fun initRecycler(){

        rvMyProProporsals.layoutManager= LinearLayoutManager(this@MyProporsals)
        proporsalAdapter = ProporsalAdapter { projectItem: Proposal ->
            projCardClicked(projectItem)
        }
        rvMyProProporsals.adapter = proporsalAdapter
        fetchDetails()

        rvMyProProporsals.visibility = View.GONE
    }

    private fun projCardClicked(projectItem: Proposal) {
        val bundle = Bundle()
        bundle.putSerializable("user", out)
        bundle.putSerializable("project", projectItem)

        val intent = Intent(this@MyProporsals, MyProjDetail::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}