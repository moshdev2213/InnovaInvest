package com.example.innova.Activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.innova.EntityRes.UserRecord
import com.example.innova.Fragment.AddProjFragment
import com.example.innova.Fragment.IndexFragment
import com.example.innova.Fragment.ProfileFragment
import com.example.innova.Fragment.ProjectFragment
import com.example.innova.Fragment.WorkFragment
import com.example.innova.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var userObj: UserRecord?=null
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val receivedUser = intent.getSerializableExtra("user", UserRecord::class.java)
        userObj = receivedUser

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        replaceFrag(IndexFragment(),userObj)

        bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.profile->replaceFrag(ProfileFragment(),userObj)
                R.id.homie->replaceFrag(IndexFragment(),userObj)
                R.id.project->replaceFrag(ProjectFragment(),userObj)
                R.id.work->replaceFrag(WorkFragment(),userObj)

                else->{

                }
            }
            true
        }
    }
    private fun replaceFrag(fragment: Fragment, userObj: UserRecord?=null){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        if(userObj !=null){
            val bundle = Bundle()
            bundle.putSerializable("user",userObj)
            fragment.arguments = bundle
        }

        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}