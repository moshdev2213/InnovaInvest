package com.example.invest.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.invest.EntityRes.ProjectItem
import com.example.invest.EntityRes.Proposal
import com.example.invest.R


class ProporsalAdapter(
    private val projCardClicked: (Proposal) -> Unit
): RecyclerView.Adapter<ProporsalAdapterViewHolder>() {
    private val projList = ArrayList<Proposal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProporsalAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.proposal_item,parent,false)
        return ProporsalAdapterViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return projList.size
    }

    override fun onBindViewHolder(holder: ProporsalAdapterViewHolder, position: Int) {
        holder.bind(projList[position],projCardClicked)
    }
    fun setList(busItem: List<Proposal>){
        projList.clear()
        projList.addAll(busItem)
        notifyDataSetChanged()
    }

}

class ProporsalAdapterViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bind(
        projectItem: Proposal,
        projectCardClicked:(Proposal)->Unit
    ){
        val cvProjectsClickDetail = view.findViewById<CardView>(R.id.cvProjectsClickDetail)
        val tvProjectsPend = view.findViewById<ImageView>(R.id.tvProjectsPend)
        val tvProjectsExp = view.findViewById<TextView>(R.id.tvProjectsExp)
        val tvProjectsGender = view.findViewById<TextView>(R.id.tvProjectsGender)
        val tvProjectsEmail = view.findViewById<TextView>(R.id.tvProjectsEmail)
        val tvProjectsName = view.findViewById<TextView>(R.id.tvProjectsName)
        val imgProjectsPic = view.findViewById<ImageView>(R.id.imgProjectsPic)

        tvProjectsPend.visibility=View.INVISIBLE

        //set image according to the keyword
        val imageResource = when (projectItem.expand.forProject.category.toString()) {
            "cyber security" -> R.drawable.cyberg
            "data analytics" -> R.drawable.dataninja
            "ecommerce" -> R.drawable.ecomm
            "finance" -> R.drawable.fintech
            "game" -> R.drawable.gme
            "healthcare" -> R.drawable.healthcat
            "education" -> R.drawable.techedu
            "virtual reality" -> R.drawable.vr

            else -> R.drawable.propobaner // Default image in case of unknown keyword
        }
        imgProjectsPic.setImageResource(imageResource)

        tvProjectsGender.text = projectItem.email
        tvProjectsExp.text = projectItem.expand.forProject.category
        tvProjectsName.text = projectItem.expand.forProject.title.capitalize()
        tvProjectsEmail.text = projectItem.expand.forProject.email


        cvProjectsClickDetail.setOnClickListener {
            projectCardClicked(projectItem)
        }
    }

}