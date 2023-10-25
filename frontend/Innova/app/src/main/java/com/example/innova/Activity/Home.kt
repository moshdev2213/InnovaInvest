package com.example.innova.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.innova.EntityRes.UserRecord
import com.example.innova.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var userObj: UserRecord?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}