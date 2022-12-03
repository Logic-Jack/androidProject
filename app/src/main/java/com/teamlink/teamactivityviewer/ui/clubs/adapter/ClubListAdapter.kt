package com.teamlink.teamactivityviewer.ui.clubs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamlink.teamactivityviewer.databinding.ClubdataListItemBinding
import com.teamlink.teamactivityviewer.ui.data.model.ClubData

class ClubListAdapter(private val onClubClicked: (ClubData) -> Unit)
    : ListAdapter<ClubData, ClubListAdapter.ClubDataViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubDataViewHolder {
        return ClubDataViewHolder(
            ClubdataListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ClubDataViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onClubClicked(current)
        }
        holder.bind(current)
    }

    class ClubDataViewHolder(private var binding: ClubdataListItemBinding)
        : RecyclerView.ViewHolder(binding.root){

            fun bind(item: ClubData){
                binding.itemName.text = item.Name
                binding.itemDesc.text = item.Description
            }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ClubData>() {
            override fun areItemsTheSame(oldItem: ClubData, newItem: ClubData): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ClubData, newItem: ClubData): Boolean {
                return oldItem.Id == newItem.Id
            }
        }
    }
}