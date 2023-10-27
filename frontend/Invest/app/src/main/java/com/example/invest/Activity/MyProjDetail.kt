package com.example.invest.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.innova.EntityRes.UserRecord
import com.example.invest.EntityRes.Proposal
import com.example.invest.R

class MyProjDetail : AppCompatActivity() {
    private var out: UserRecord? = null
    private lateinit var proprosal:Proposal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_proj_detail)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
            proprosal = bundle.getSerializable("project") as Proposal
        }
    }
}