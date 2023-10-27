package com.example.innova.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.innova.EntityRes.UserRecord
import com.example.innova.R

class Success : AppCompatActivity() {
    private var out: UserRecord? = null
    private lateinit var cvProceedToHomeAct:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
        }

        cvProceedToHomeAct = findViewById(R.id.cvProceedToHomeAct)

        cvProceedToHomeAct.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", out)

            val intent = Intent(this@Success, Home::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
    }
}