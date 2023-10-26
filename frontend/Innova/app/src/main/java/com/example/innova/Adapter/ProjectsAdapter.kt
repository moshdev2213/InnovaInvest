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
import com.example.innova.R

class ProjectsAdapter(
    private val projCardClicked: (ProjectItem) -> Unit,
    contexts: Context
): RecyclerView.Adapter<BusAdapterViewHolder>() {
    val conts = contexts
    private val projList = ArrayList<ProjectItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.proposal_item,parent,false)
        return BusAdapterViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return projList.size
    }

    override fun onBindViewHolder(holder: BusAdapterViewHolder, position: Int) {
        holder.bind(projList[position],projCardClicked, conts)
    }
    fun setList(busItem: List<ProjectItem>){
        projList.clear()
        projList.addAll(busItem)
        notifyDataSetChanged()
    }
}

class BusAdapterViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
    fun bind(projectItem: ProjectItem,projectCardClicked:(ProjectItem)->Unit,context: Context){
        val cvProjectsClickDetail = view.findViewById<CardView>(R.id.cvProjectsClickDetail)
        val tvProjectsPend = view.findViewById<ImageView>(R.id.tvProjectsPend)
        val tvProjectsExp = view.findViewById<TextView>(R.id.tvProjectsExp)
        val tvProjectsGender = view.findViewById<TextView>(R.id.tvProjectsGender)
        val tvProjectsEmail = view.findViewById<TextView>(R.id.tvProjectsEmail)
        val tvProjectsName = view.findViewById<TextView>(R.id.tvProjectsName)
        val imgProjectsPic = view.findViewById<ImageView>(R.id.imgProjectsPic)

        //set image according to the keyword
        val imageResource = when (projectItem.category.lowercase()) {
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
        tvProjectsExp.text = projectItem.category.capitalize()
        tvProjectsName.text = projectItem.title.capitalize()
        tvProjectsEmail.text = projectItem.description

        cvProjectsClickDetail.setOnClickListener {
            projectCardClicked(projectItem)
        }
    }

}