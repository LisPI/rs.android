package com.develop.rs_school.workingwithstorage

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.develop.rs_school.workingwithstorage.database.Friend
import kotlin.math.roundToInt

class FriendRecyclerAdapter : RecyclerView.Adapter<FriendRecyclerAdapter.ViewHolder>() {

    var friends = listOf<Friend>()
        set(value){
            field = value
            notifyDataSetChanged()
        }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.NameTv)
        val city: TextView = itemView.findViewById(R.id.CityTv)
        val dob : TextView = itemView.findViewById(R.id.BODTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = friends.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = friends[position].name
        holder.city.text = friends[position].city
        holder.dob.text = friends[position].DOB.toString()
    }
}