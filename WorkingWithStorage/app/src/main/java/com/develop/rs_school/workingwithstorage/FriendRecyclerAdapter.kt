package com.develop.rs_school.workingwithstorage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.develop.rs_school.workingwithstorage.database.Friend
import java.text.SimpleDateFormat
import java.util.*

class FriendRecyclerAdapter(private val itemClickListener: FriendRecyclerItemListener) : ListAdapter<Friend, FriendRecyclerAdapter.ViewHolder>(FriendDiffUtilCallback()) {

    companion object{
        const val dateFormatForRecyclerItem = "d MMMM yyyy"
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val name: TextView = itemView.findViewById(R.id.name_tv)
        private val city: TextView = itemView.findViewById(R.id.city_tv)
        private val dob : TextView = itemView.findViewById(R.id.dob_tv)

        fun bind(friend: Friend, itemClickListener : FriendRecyclerItemListener) {
            name.text = friend.name
            city.text = friend.city
            val specialDateFormat = SimpleDateFormat(dateFormatForRecyclerItem, Locale.getDefault())
            dob.text = specialDateFormat.format(friend.DOB)

            itemView.setOnClickListener{itemClickListener.onClick(friend)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
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
