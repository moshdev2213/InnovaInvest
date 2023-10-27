package com.example.innova.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innova.Adapter.ProjSkillAadpter
import com.example.innova.EntityRes.ProjectItem
import com.example.innova.EntityRes.UserRecord
import com.example.innova.R
import java.text.SimpleDateFormat
import java.util.Locale

class AllProjDetails : AppCompatActivity() {
    private lateinit var rvAllProjSkillSet: RecyclerView
    private lateinit var projSkillAadpter: ProjSkillAadpter

    private lateinit var tvAllProjDescLong: TextView
    private lateinit var tvAllProjToDetail: TextView
    private lateinit var tvAllProjFromDetail: TextView
    private lateinit var tvAllProjTypeDetail: TextView
    private lateinit var tvAllProjNameDetail: TextView
    private lateinit var tvSeatCal: TextView
    private lateinit var tvAllProjCarb: TextView
    private lateinit var imgBannerAllProjDetail: ImageView
    private lateinit var imgBackBtn: ImageView
    private lateinit var cvBackBtnMyAllProj: CardView
    private var out: UserRecord? = null
    private var project: ProjectItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_proj_details)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
            project = bundle.getSerializable("project") as ProjectItem
        }

        imgBackBtn = findViewById(R.id.imgBackBtn)
        imgBannerAllProjDetail = findViewById(R.id.imgBannerAllProjDetail)
        tvAllProjNameDetail = findViewById(R.id.tvAllProjNameDetail)
        tvAllProjTypeDetail = findViewById(R.id.tvAllProjTypeDetail)
        tvAllProjFromDetail = findViewById(R.id.tvAllProjFromDetail)
        tvAllProjToDetail = findViewById(R.id.tvAllProjToDetail)
        tvAllProjDescLong = findViewById(R.id.tvAllProjDescLong)
        tvAllProjCarb = findViewById(R.id.tvAllProjCarb)
        rvAllProjSkillSet = findViewById(R.id.rvAllProjSkillSet)
        imgBackBtn = findViewById(R.id.imgBackBtn)
        tvSeatCal = findViewById(R.id.tvSeatCal)
        cvBackBtnMyAllProj = findViewById(R.id.cvBackBtnMyAllProj)

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
        imgBannerAllProjDetail.setImageResource(imageResource)
        tvAllProjTypeDetail.text = project?.email
        tvAllProjNameDetail.text =project?.title?.capitalize()
        tvSeatCal.text = "${project?.category?.uppercase()}"
        tvAllProjCarb.text = "${formatDateString(project?.created)}"
        tvAllProjFromDetail.text = "Rs.${project?.budget}"
        tvAllProjToDetail.text = "${project?.timeLine}"
        tvAllProjDescLong.text = project?.description?.capitalize()
        println(project?.created)

        cvBackBtnMyAllProj.setOnClickListener {
            finish()
        }
        imgBackBtn.setOnClickListener {
            finish()
        }
        initRecycler(project)
    }
    private fun initRecycler(project:ProjectItem?){

        rvAllProjSkillSet.layoutManager= LinearLayoutManager(this@AllProjDetails)
        projSkillAadpter = ProjSkillAadpter ()
        rvAllProjSkillSet.adapter = projSkillAadpter

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