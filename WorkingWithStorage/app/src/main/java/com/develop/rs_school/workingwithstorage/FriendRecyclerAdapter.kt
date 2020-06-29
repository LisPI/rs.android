package com.develop.rs_school.workingwithstorage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.develop.rs_school.workingwithstorage.database.Friend
import com.develop.rs_school.workingwithstorage.databinding.RecyclerViewItemBinding
import java.text.SimpleDateFormat
import java.util.*

class FriendRecyclerAdapter(private val itemClickListener: FriendRecyclerItemListener) :
    ListAdapter<Friend, FriendRecyclerAdapter.ViewHolder>(FriendDiffUtilCallback()) {

    companion object {
        const val dateFormatForRecyclerItem = "d MMMM yyyy"
    }

    class ViewHolder(private val itemBinding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(friend: Friend, itemClickListener: FriendRecyclerItemListener) {
            itemBinding.nameTv.text = friend.name
            itemBinding.cityTv.text = friend.city
            val specialDateFormat = SimpleDateFormat(dateFormatForRecyclerItem, Locale.getDefault())
            itemBinding.dobTv.text = specialDateFormat.format(friend.DOB)

            itemView.setOnClickListener { itemClickListener.onClick(friend) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecyclerViewItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = getItem(position)
        holder.bind(friend, itemClickListener)
    }
}

class FriendDiffUtilCallback : DiffUtil.ItemCallback<Friend>() {
    override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
        return oldItem == newItem
    }
}

class FriendRecyclerItemListener(val clickListener: (FriendId: Int) -> Unit) {
    fun onClick(friend: Friend) = friend.id?.let { clickListener(it) }
}
