package com.develop.rs_school.thecatapi.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.develop.rs_school.thecatapi.R
import com.develop.rs_school.thecatapi.databinding.CatRecyclerItemBinding
import com.develop.rs_school.thecatapi.network.Cat

class CatRecyclerAdapter(private val itemClickListener: CatRecyclerItemListener) :
    PagingDataAdapter<Cat, CatRecyclerAdapter.ViewHolder>(CatDiffUtilCallback()) {

    private companion object {
        private const val GLIDE_THUMBNAIL_SIZE = 0.1f
    }

    inner class ViewHolder(private val itemBinding: CatRecyclerItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemView.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { itemClickListener.onClick(it) }
            }
        }

        fun bind(cat: Cat) {
            Glide.with(itemView.context).load(cat.imageUrl)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.connection_error)
                )
                .thumbnail(GLIDE_THUMBNAIL_SIZE)
                .into(itemBinding.catImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CatRecyclerItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cat = getItem(position)
        cat?.let { holder.bind(it) }
    }
}

class CatDiffUtilCallback : DiffUtil.ItemCallback<Cat>() {
    override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.id == newItem.id
    }
}

class CatRecyclerItemListener(val clickListener: (cat: Cat) -> Unit) {
    fun onClick(cat: Cat) = clickListener(cat)
}
