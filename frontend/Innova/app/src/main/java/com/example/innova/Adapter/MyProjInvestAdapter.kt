package com.example.innova.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.innova.EntityRes.ProjectItem
import com.example.innova.EntityRes.Proposal
import com.example.innova.R

class MyProjInvestAdapter(
    private val projCardClicked: (Proposal) -> Unit,
    contexts: Context
): RecyclerView.Adapter<MyProjInvestAdapterViewHolder>() {
    val conts = contexts
    private val projList = ArrayList<Proposal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProjInvestAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.investor_proposal_item,parent,false)
        return MyProjInvestAdapterViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return projList.size
    }

    override fun onBindViewHolder(holder: MyProjInvestAdapterViewHolder, position: Int) {
        holder.bind(projList[position],projCardClicked, conts)
    }
    fun setList(busItem: List<Proposal>){
        projList.clear()
        projList.addAll(busItem)
        notifyDataSetChanged()
    }


}

class MyProjInvestAdapterViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bind(
        projectItem: Proposal,
        projectCardClicked:(Proposal)->Unit,
        context: Context
    ){
        val cvMyProjInvestCard = view.findViewById<CardView>(R.id.cvMyProjInvestCard)
        val tvMyProjInvestStatus = view.findViewById<TextView>(R.id.tvMyProjInvestStatus)
        val tvBudgetMyProJInvest = view.findViewById<TextView>(R.id.tvBudgetMyProJInvest)
        val tvMyProjInvestName = view.findViewById<TextView>(R.id.tvMyProjInvestName)
        val imgBannerInvester = view.findViewById<ImageView>(R.id.imgBannerInvester)

        tvMyProjInvestStatus.text = "${ projectItem.status }"
        tvBudgetMyProJInvest.text = "Budget Rs.${ projectItem.budget }"
        tvMyProjInvestName.text = "${ projectItem.email.lowercase() }"

        cvMyProjInvestCard.setOnClickListener {
            projectCardClicked(projectItem)
        }
    }

}