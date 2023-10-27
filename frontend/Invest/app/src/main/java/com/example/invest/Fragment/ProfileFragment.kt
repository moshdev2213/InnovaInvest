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
import com.example.innova.EntityRes.UserRecord
import com.example.invest.Activity.EditProfile
import com.example.invest.Activity.MyProporsals
import com.example.invest.Activity.SignIn
import com.example.invest.R

class ProfileFragment : Fragment() {

    private var out: UserRecord? = null
    private lateinit var cvDigitalTokenUser: CardView
    private lateinit var cvProceedTodlt: CardView
    private lateinit var cvBrowseInvnt: CardView
    private lateinit var cvBrowsePropo: CardView
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        out = arguments?.getSerializable("user", UserRecord::class.java)!!

        cvBrowsePropo = view.findViewById(R.id.cvBrowsePropo)
//        cvBrowseInvnt = view.findViewById(R.id.cvBrowseInvnt)
        cvDigitalTokenUser = view.findViewById(R.id.cvDigitalTokenUser)
        cvProceedTodlt = view.findViewById(R.id.cvProceedTodlt)

        cvBrowsePropo.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", out)

            val intent = Intent(requireActivity(), MyProporsals::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        cvDigitalTokenUser.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", out)

            val intent = Intent(requireActivity(), EditProfile::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        cvProceedTodlt.setOnClickListener {
            val intent = Intent(requireContext(), SignIn::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
        return view
    }
}