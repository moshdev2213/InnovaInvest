package com.example.innova.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.innova.EntityRes.UserRecord
import com.example.invest.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class IndexFragment : Fragment() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var out: UserRecord? = null
    private lateinit  var btnSeeWrk: Button
    private lateinit  var btnBrowseReserves: Button
    private lateinit  var btnBrowseProjects02: Button
    private lateinit  var btnShowProjects: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_index, container, false)
        bottomNavigationView =  requireActivity().findViewById(R.id.bottomNavigationView)
        btnSeeWrk =  view.findViewById(R.id.btnSeeWrk)
        btnBrowseReserves =  view.findViewById(R.id.btnBrowseReserves)
        btnBrowseProjects02 = view.findViewById(R.id.btnBrowseProjects02)
        btnShowProjects =  view.findViewById(R.id.btnShowProjects)

        out = requireArguments().getSerializable("user") as UserRecord?

        btnShowProjects.setOnClickListener(View.OnClickListener {
            reDirectAccountPage()
            bottomNavigationView.selectedItemId = R.id.profile
        })

        btnBrowseReserves.setOnClickListener(View.OnClickListener {
            reDirectAccountPage()
            bottomNavigationView.selectedItemId = R.id.project
        })

        btnSeeWrk.setOnClickListener(View.OnClickListener {
            reDirectAccountPage()
            bottomNavigationView.selectedItemId = R.id.work
        })

        btnBrowseProjects02.setOnClickListener(View.OnClickListener {
            reDirectAccountPage()
            bottomNavigationView.selectedItemId = R.id.project
        })

        return view
    }
    private fun reDirectAccountPage() {
        replaceFrag(ProfileFragment(), out)
    }

    private fun reDirectProjectsPage() {
        replaceFrag(ProjectFragment(), out)
    }

    private fun reWorkPage() {
        replaceFrag(WorkFragment(), out)
    }

    private fun replaceFrag(fragment: Fragment, out: UserRecord?) {
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        if (out != null) {
            val bundle = Bundle()
            bundle.putSerializable("user", out)
            fragment.arguments = bundle
        }
        transaction.replace(
            R.id.frame_layout,
            fragment
        ) // Replace the current Fragment with FragmentB
        transaction.commit()
    }
}