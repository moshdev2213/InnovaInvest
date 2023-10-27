package com.example.innova.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innova.Adapter.ProjSkillAadpter
import com.example.innova.Adapter.ProjectsAdapter
import com.example.innova.EntityRes.ProjectItem
import com.example.innova.EntityRes.UserRecord
import com.example.innova.R
import java.text.SimpleDateFormat
import java.util.Locale

class ProjectDetail : AppCompatActivity() {
    private lateinit var rvProjSkillSet:RecyclerView
    private lateinit var projSkillAadpter: ProjSkillAadpter

    private lateinit var tvProjDescLong:TextView
    private lateinit var tvProjToDetail:TextView
    private lateinit var tvProjFromDetail:TextView
    private lateinit var tvProjTypeDetail:TextView
    private lateinit var tvProjNameDetail:TextView
    private lateinit var tvSeatCal:TextView
    private lateinit var tvProjCarb:TextView
    private lateinit var imgBannerProjDetail:ImageView
    private lateinit var imgBackBtn:ImageView
    private lateinit var cvBuyBtn:CardView
    private lateinit var cvBackBtnMyProj:CardView
    private var out: UserRecord? = null
    private var project: ProjectItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
            project = bundle.getSerializable("project") as ProjectItem
        }

        imgBackBtn = findViewById(R.id.imgBackBtn)
        imgBannerProjDetail = findViewById(R.id.imgBannerProjDetail)
        tvProjNameDetail = findViewById(R.id.tvProjNameDetail)
        tvProjTypeDetail = findViewById(R.id.tvProjTypeDetail)
        tvProjFromDetail = findViewById(R.id.tvProjFromDetail)
        tvProjToDetail = findViewById(R.id.tvProjToDetail)
        tvProjDescLong = findViewById(R.id.tvProjDescLong)
        tvProjCarb = findViewById(R.id.tvProjCarb)
        rvProjSkillSet = findViewById(R.id.rvProjSkillSet)
        imgBackBtn = findViewById(R.id.imgBackBtn)
        tvSeatCal = findViewById(R.id.tvSeatCal)
        cvBackBtnMyProj = findViewById(R.id.cvBackBtnMyProj)
        cvBuyBtn = findViewById(R.id.cvBuyBtn)

        //set image according to the keyword
        val imageResource = when (project?.category?.lowercase()) {
            "cyber security" -> R.drawable.cyberg
            "data analytics" -> R.drawable.dataninja
            "ecommerce" -> R.drawable.ecomm
            "finance" -> R.drawable.fintech
            "game" -> R.drawable.gme
            "healthcare" -> R.drawable.healthcat
            "education" -> R.drawable.techedu
            "virtual reality" -> R.drawable.vr

            else -> R.drawable.propobaner // Default image in case of unknown keyword
        }
        imgBannerProjDetail.setImageResource(imageResource)
        tvProjTypeDetail.text = project?.email
        tvProjNameDetail.text =project?.title?.capitalize()
        tvSeatCal.text = "${project?.category?.uppercase()}"
        tvProjCarb.text = "${formatDateString(project?.created)}"
        tvProjFromDetail.text = "Rs.${project?.budget}"
        tvProjToDetail.text = "${project?.timeLine}"
        tvProjDescLong.text = project?.description?.capitalize()
        println(project?.created)
        cvBuyBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", out)
            bundle.putSerializable("project", project)

            val intent = Intent(this@ProjectDetail, InvestorProporsal::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        cvBackBtnMyProj.setOnClickListener {
            finish()
        }
        imgBackBtn.setOnClickListener {
            finish()
        }
        initRecycler(project)
    }
    private fun initRecycler(project:ProjectItem?){

        rvProjSkillSet.layoutManager= LinearLayoutManager(this@ProjectDetail)
        projSkillAadpter = ProjSkillAadpter ()
        rvProjSkillSet.adapter = projSkillAadpter

        // Split the "skillsRequired" string by commas and store in a list
        val skillsList = project?.skillsRequired?.split(",")

        if (skillsList != null) {
            projSkillAadpter.setList(skillsList)
        }

    }
    private fun formatDateString(inputDateString: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        try {
            val date = inputFormat.parse(inputDateString)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return inputDateString // Return the original string in case of an error
        }
    }
}