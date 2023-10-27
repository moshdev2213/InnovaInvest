package com.example.innova.Activity

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
import com.example.innova.ApiService.ProjectService
import com.example.innova.DialogAlerts.ApiProgress
import com.example.innova.EntityRes.ProjTotalBudget
import com.example.innova.EntityRes.ProjectItem
import com.example.innova.EntityRes.ProjectsRes
import com.example.innova.EntityRes.UserRecord
import com.example.innova.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProporsals : AppCompatActivity() {
    private var out: UserRecord? = null
    private lateinit var rvMyProProporsals:RecyclerView
    private lateinit var projectsAdapter:ProjectsAdapter
    private lateinit var apiProgress: ApiProgress
    private lateinit var fabAddMyProj: FloatingActionButton
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
        fabAddMyProj = findViewById(R.id.fabAddMyProj)
        imgBackToProfileFromMy = findViewById(R.id.imgBackToProfileFromMy)

        fabAddMyProj.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", out)

            val intent = Intent(this@MyProporsals, AddProject::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
        imgBackToProfileFromMy.setOnClickListener {
            finish()
        }

        initRecycler()
    }
    private fun initRecycler(){

        rvMyProProporsals.layoutManager= LinearLayoutManager(this@MyProporsals)
        projectsAdapter = ProjectsAdapter ({
                projectItem: ProjectItem -> projCardClicked(projectItem)
        },this@MyProporsals )
        rvMyProProporsals.adapter = projectsAdapter
        fetchDetails()

        rvMyProProporsals.visibility = View.GONE
    }

    private fun projCardClicked(projectItem: ProjectItem) {
        val bundle = Bundle()
        bundle.putSerializable("user", out)
        bundle.putSerializable("project", projectItem)

        val intent = Intent(this@MyProporsals, ProjectDetail::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
    private fun fetchDetails() {
        apiProgress = ApiProgress(this@MyProporsals)
        apiProgress.startProgressLoader()

        val emailToFilter: String = out?.record?.email ?: ""
        val filterValue = "(email=\"$emailToFilter\")"

        val retrofitService= RetrofitService()
        val getList =retrofitService.getRetrofit().create(ProjectService::class.java)
        val call: Call<ProjectsRes> = getList.getProjNotUser(filterValue)

        call.enqueue(object : Callback<ProjectsRes> {
            override fun onResponse(call: Call<ProjectsRes>, response: Response<ProjectsRes>) {
                if(response.isSuccessful){
                    if (response.body()!=null){
                        println(response.body())
                        val mealRes = response.body()
                        val mealItem = mealRes?.items

                        getProjTotBudById()
                        projectsAdapter.setList(mealItem!!)
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
            override fun onFailure(call: Call<ProjectsRes>, t: Throwable) {
                Toast.makeText(this@MyProporsals,"Server Error", Toast.LENGTH_SHORT).show()
                apiProgress.dismissProgressLoader()
            }
        })
    }
    private fun getProjTotBudById() {
//        val filterValue = "(forProject.id=\"$id\")"
        val expand = "forProject.budget,forProject.id"
        val fields = "budget,forProject"

        val retrofitService= RetrofitService()
        val getList =retrofitService.getRetrofit().create(ProjectService::class.java)
        val call: Call<ProjTotalBudget> = getList.getProjTotBudById(expand,fields)

        call.enqueue(object : Callback<ProjTotalBudget> {
            override fun onResponse(call: Call<ProjTotalBudget>, response: Response<ProjTotalBudget>) {
                if(response.isSuccessful){
                    if (response.body()!=null){
                        println(response.body())
                        val mealRes = response.body()
                        val mealItem = mealRes?.items

//                        val keyValuePairs = mealItem?.map { item ->
//                            Pair(item.forProject,item.budget)
//                        }
                        // Assuming mealItem is a list of your data items
                        val groupedItems = mealItem?.groupBy { it.forProject }

                        val keyValuePairs = groupedItems?.map { (project, items) ->
                            val totalBudget = items.sumOf { it.budget }
                            Pair(project, totalBudget)
                        }

                        if (keyValuePairs != null) {
                            projectsAdapter.setKeyValuePairs(keyValuePairs)
                        }
                    }else{
                        Toast.makeText(this@MyProporsals,"Empty Data", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@MyProporsals,"Invalid response", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ProjTotalBudget>, t: Throwable) {
                Toast.makeText(this@MyProporsals,"Server Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}