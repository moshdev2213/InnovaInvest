package com.example.innova.Fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.charmrides.RetrofitService.RetrofitService
import com.example.fitme.DialogAlerts.ProgressLoader
import com.example.innova.Activity.ProjectDetail
import com.example.innova.Adapter.ProjectsAdapter
import com.example.innova.ApiService.ProjectService
import com.example.innova.DialogAlerts.ApiProgress
import com.example.innova.EntityRes.ProjectItem
import com.example.innova.EntityRes.ProjectsRes
import com.example.innova.EntityRes.UserRecord
import com.example.innova.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProjectFragment : Fragment() {
    private lateinit var rvReportFrag: RecyclerView
    private lateinit var textView22: TextView
    private lateinit var projectsAdapter:ProjectsAdapter
    private lateinit var out: UserRecord
    private lateinit var apiProgress: ApiProgress
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_project, container, false)

        out = arguments?.getSerializable("user", UserRecord::class.java)!!
        initRecycler(view)

        return  view
    }
    private fun initRecycler(view:View){
        rvReportFrag = view.findViewById(R.id.rvBusFrag)
        textView22 = view.findViewById(R.id.textView22)

        rvReportFrag.layoutManager= LinearLayoutManager(requireActivity())
        projectsAdapter = ProjectsAdapter ({
                projectItem: ProjectItem -> projCardClicked(projectItem)
        },requireContext() )
        rvReportFrag.adapter = projectsAdapter
        fetchDetails()

        textView22.visibility = View.GONE
        rvReportFrag.visibility = View.GONE
    }

    private fun projCardClicked(projectItem: ProjectItem) {
        val bundle = Bundle()
        bundle.putSerializable("user", out)
        bundle.putSerializable("project", projectItem)

        val intent = Intent(requireActivity(), ProjectDetail::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
    private fun fetchDetails() {
        apiProgress = ApiProgress(requireContext())
        apiProgress.startProgressLoader()

        val emailToFilter: String = out.record.email
        val filterValue = "(email!=\"$emailToFilter\")"

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
                        projectsAdapter.setList(mealItem!!)
                        rvReportFrag.visibility=View.VISIBLE
                        textView22.visibility = View.GONE
                        apiProgress.dismissProgressLoader()
                    }else{
                        Toast.makeText(requireContext(),"Empty Data", Toast.LENGTH_SHORT).show()
                        textView22.visibility = View.VISIBLE
                        apiProgress.dismissProgressLoader()
                    }

                }else{
                    Toast.makeText(requireContext(),"Invalid response", Toast.LENGTH_SHORT).show()
                    textView22.visibility = View.VISIBLE
                    apiProgress.dismissProgressLoader()
                }
            }
            override fun onFailure(call: Call<ProjectsRes>, t: Throwable) {
                Toast.makeText(requireContext(),"Server Error", Toast.LENGTH_SHORT).show()
                textView22.visibility = View.VISIBLE
                apiProgress.dismissProgressLoader()
            }
        })
    }
}