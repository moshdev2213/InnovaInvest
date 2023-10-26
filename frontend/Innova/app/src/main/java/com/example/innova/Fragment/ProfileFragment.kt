package com.example.innova.Fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.example.innova.Activity.MyProporsals
import com.example.innova.Activity.ProjectDetail
import com.example.innova.EntityRes.UserRecord
import com.example.innova.R


class ProfileFragment : Fragment() {
    private var out: UserRecord? = null
    private lateinit var cvProceedToMyProj:CardView

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_profile, container, false)
        out = arguments?.getSerializable("user", UserRecord::class.java)!!

        cvProceedToMyProj = view.findViewById(R.id.cvProceedToMyProj)

        cvProceedToMyProj.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", out)

            val intent = Intent(requireActivity(), MyProporsals::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        return view
    }
}