package com.capstone.kiwinform.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.kiwinform.databinding.ItemActivitiesPlanBinding
import java.time.format.DateTimeFormatter

class ListActivitiesPlanAdapter : RecyclerView.Adapter<ListActivitiesPlanAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Plan)
    }

    private var list = emptyList<Plan>()

    fun setList(plans: List<Plan>) {
        this.list = plans
        notifyDataSetChanged()
    }

    class ListViewHolder(var binding: ItemActivitiesPlanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemActivitiesPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val plan = list[position]
        holder.binding.apply {
            activitiesPlanTitle.text = plan.title
            activitiesPlan.text = plan.description
            activitiesPlanTime.text = plan.time.toString()

            val date = plan.date
            val formattedDate = date.format(DateTimeFormatter.ofPattern("EEEE, d MMM yyyy"))
            activitiesPlanDate.text = formattedDate
        }

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(list[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = list.size
}