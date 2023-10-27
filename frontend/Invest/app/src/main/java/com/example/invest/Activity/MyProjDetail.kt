package com.example.invest.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.innova.EntityRes.UserRecord
import com.example.invest.EntityRes.Proposal
import com.example.invest.R
import java.text.SimpleDateFormat
import java.util.Locale

class MyProjDetail : AppCompatActivity() {
    private var out: UserRecord? = null
    private lateinit var proprosal:Proposal

    private lateinit var tvProjPropDescLong:TextView
    private lateinit var tvMyrojPropToDetail:TextView
    private lateinit var tvMyrojPropFromDetail:TextView
    private lateinit var tvMyrojPropType:TextView
    private lateinit var tvMyrojPropName:TextView
    private lateinit var tvProjPropCarb:TextView
    private lateinit var tvProjPropFat:TextView
    private lateinit var tvSeatCal:TextView
    private lateinit var tvMyrojPropNameDetail:TextView

    private lateinit var imgBannerProjPropDetail:ImageView
    private lateinit var imgBackBtn:ImageView
    private lateinit var imgBannerProjProp:ImageView

    private lateinit var cvBuyBtn:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_proj_detail)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
            proprosal = bundle.getSerializable("project") as Proposal
        }

        tvMyrojPropFromDetail = findViewById(R.id.tvMyrojPropFromDetail)
        tvProjPropDescLong = findViewById(R.id.tvProjPropDescLong)
        tvMyrojPropToDetail = findViewById(R.id.tvMyrojPropToDetail)
        tvMyrojPropType = findViewById(R.id.tvMyrojPropType)
        tvMyrojPropName = findViewById(R.id.tvMyrojPropName)
        tvProjPropCarb = findViewById(R.id.tvProjPropCarb)
        tvProjPropFat = findViewById(R.id.tvProjPropFat)
        tvSeatCal = findViewById(R.id.tvSeatCal)
        tvMyrojPropNameDetail = findViewById(R.id.tvMyrojPropNameDetail)
        imgBannerProjPropDetail = findViewById(R.id.imgBannerProjPropDetail)
        imgBackBtn = findViewById(R.id.imgBackBtn)
        imgBannerProjProp = findViewById(R.id.imgBannerProjProp)
        cvBuyBtn = findViewById(R.id.cvBuyBtn)

        imgBackBtn.setOnClickListener {
            finish()
        }
        cvBuyBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", out)
            bundle.putSerializable("project", proprosal)

            val intent = Intent(this@MyProjDetail, ApplicationTracking::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        //set image according to the keyword
        val imageResource = when (proprosal?.expand?.forProject?.category?.lowercase()) {
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
        imgBannerProjPropDetail.setImageResource(imageResource)

        tvMyrojPropFromDetail.text =  proprosal?.expand?.forProject?.email
        tvMyrojPropNameDetail.text = proprosal?.expand?.forProject?.title?.capitalize()
        tvSeatCal.text = "${proprosal?.expand?.forProject?.category?.uppercase()}"
        tvProjPropCarb.text = "${formatDateString(proprosal.expand?.forProject?.created)}"
        tvMyrojPropFromDetail.text = "Rs.${ proprosal?.expand?.forProject?.budget }"
        tvMyrojPropToDetail.text = "${proprosal?.expand?.forProject?.timeLine}"
        tvProjPropDescLong.text = proprosal?.expand?.forProject?.description?.capitalize()
        tvMyrojPropName.text = proprosal?.expand?.forProject?.email
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