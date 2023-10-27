package com.example.invest.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.innova.EntityRes.UserRecord
import com.example.invest.EntityRes.Proposal
import com.example.invest.R
import java.text.SimpleDateFormat
import java.util.Locale

class ApplicationTracking : AppCompatActivity() {
    private var out: UserRecord? = null
    private lateinit var proprosal:Proposal

    private lateinit var propSubTime:TextView
    private lateinit var propSubmitDate:TextView
    private lateinit var entRevTime:TextView
    private lateinit var ntRevDate:TextView
    private lateinit var entAppTime:TextView
    private lateinit var entAppDate:TextView
    private lateinit var cvProceedToHomeTrack:CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_tracking)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
            proprosal = bundle.getSerializable("project") as Proposal
        }
        propSubTime = findViewById(R.id.propSubTime)
        propSubmitDate = findViewById(R.id.propSubmitDate)
        entRevTime = findViewById(R.id.entRevTime)
        ntRevDate = findViewById(R.id.ntRevDate)
        entAppTime = findViewById(R.id.entAppTime)
        entAppDate = findViewById(R.id.entAppDate)
        cvProceedToHomeTrack = findViewById(R.id.cvProceedToHomeTrack)

        propSubTime.text = formatTime(proprosal?.created)
        propSubmitDate.text = formatDateString(proprosal?.created)

        entRevTime.text = formatTime(proprosal?.created)
        ntRevDate.text = formatDateString(proprosal?.created)

        entAppTime.text = formatTime(proprosal?.updated)
        entAppDate.text = formatDateString(proprosal?.updated)


        cvProceedToHomeTrack.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", out)

            val intent = Intent(this@ApplicationTracking, Home::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
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
    private fun formatTime(inputDateString: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.US)

        try {
            val date = inputFormat.parse(inputDateString)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return inputDateString // Return the original string in case of an error
        }
    }

}