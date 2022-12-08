package com.capstone.kiwinform.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.kiwinform.databinding.ItemPlanBinding

class ListPlanAdapter : RecyclerView.Adapter<ListPlanAdapter.ListViewHolder>() {
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

    class ListViewHolder(var binding: ItemPlanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =ItemPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val plan = list[position]
        holder.binding.apply {
            activityTitle.text = plan.title
            activity.text = plan.description
            activityTime.text = plan.time.toString()
        }

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(list[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = list.size
}