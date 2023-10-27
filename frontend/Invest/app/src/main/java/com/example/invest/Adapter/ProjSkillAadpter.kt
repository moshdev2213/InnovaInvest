package com.example.innova.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.invest.R

class ProjSkillAadpter(): RecyclerView.Adapter<SkillAdapterViewHolder>() {

    private val projList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.prev_invest,parent,false)
        return SkillAdapterViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return projList.size
    }

    override fun onBindViewHolder(holder: SkillAdapterViewHolder, position: Int) {
        holder.bind(projList[position])
    }
    fun setList(busItem: List<String>){
        projList.clear()
        projList.addAll(busItem)
        notifyDataSetChanged()
    }
}

class SkillAdapterViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
    fun bind(projectItem: String){
        val tvTheRelevantame = view.findViewById<TextView>(R.id.tvTheRelevantame)
        tvTheRelevantame.text = projectItem
    }

}