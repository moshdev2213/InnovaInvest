package com.example.innova.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.innova.EntityRes.ProjectItem
import com.example.innova.EntityRes.Proposal
import com.example.innova.EntityRes.UserRecord
import com.example.innova.R

class InvestorDetail : AppCompatActivity() {
    private var out: UserRecord? = null
    private var project: ProjectItem? = null
    private var proposal: Proposal? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_investor_detail)

        val bundle = intent.extras
        if (bundle != null) {
            // Retrieve the user and bus items
            out = bundle.getSerializable("user") as UserRecord
            project = bundle.getSerializable("project") as ProjectItem
            proposal = bundle.getSerializable("proposal") as Proposal
        }
    }
}