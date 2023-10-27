package com.example.innova.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.innova.EntityRes.Proposal
import com.example.innova.R


class PrevProjAdapter(): RecyclerView.Adapter<PrevProjAdapterViewHolder>() {

    private val projList = ArrayList<Proposal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrevProjAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.prev_invest,parent,false)
        return PrevProjAdapterViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return projList.size
    }

    override fun onBindViewHolder(holder: PrevProjAdapterViewHolder, position: Int) {
        holder.bind(projList[position])
    }
    fun setList(busItem: List<Proposal>){
        projList.clear()
        projList.addAll(busItem)
        notifyDataSetChanged()
    }
}

class PrevProjAdapterViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
    fun bind(projectItem: Proposal){
        val tvTheRelevantame = view.findViewById<TextView>(R.id.tvTheRelevantame)
        tvTheRelevantame.text = projectItem.expand.forProject.title.capitalize()
    }

}