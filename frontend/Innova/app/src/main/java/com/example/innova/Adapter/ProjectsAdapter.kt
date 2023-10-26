package com.example.innova.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    fun bind(busItem: ProjectItem,busCardClicked:(ProjectItem)->Unit,context: Context){
        val tvPriceBus = view.findViewById<TextView>(R.id.tvPriceBus)
        val tvBusName = view.findViewById<TextView>(R.id.tvBusName)
        val cvBusItemCard = view.findViewById<CardView>(R.id.cvBusItemCard)

        tvPriceBus.text = "Rs.${busItem.price}"
        tvBusName.text = busItem.name.capitalize()

        cvBusItemCard.setOnClickListener {
            busCardClicked(busItem)
        }
    }

}