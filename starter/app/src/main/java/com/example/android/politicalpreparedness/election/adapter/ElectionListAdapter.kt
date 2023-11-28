package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ItemElectionBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener) :
    ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    // TODO: Bind ViewHolder
    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val election = getItem(position)
        holder.bind(election, clickListener)
//        holder.itemView.setOnClickListener {
//            clickListener.onClick(election)
//        }
    }

    // TODO: Add companion object to inflate ViewHolder (from)
}

// TODO: Create ElectionViewHolder

class ElectionViewHolder(private val binding: ItemElectionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    // Add companion object to inflate ViewHolder (from)
    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ElectionViewHolder(ItemElectionBinding.inflate(inflater, parent, false))
        }
    }

    fun bind(election: Election, clickListener: ElectionListener) {
        binding.election = election
        binding.listener = clickListener
        binding.executePendingBindings()
    }
}

// TODO: Create ElectionDiffCallback

class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }
}

// TODO: Create ElectionListener

class ElectionListener(val clickListener: (Election) -> Unit) {
    fun onClick(election: Election) = clickListener(election)
}