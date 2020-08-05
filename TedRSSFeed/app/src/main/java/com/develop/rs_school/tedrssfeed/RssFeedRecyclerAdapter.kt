package com.develop.rs_school.tedrssfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.develop.rs_school.tedrssfeed.databinding.RssRecyclerItemBinding

class RssFeedRecyclerAdapter(private val itemClickListener: RssRecyclerItemListener) :
    ListAdapter<RssItem, RssFeedRecyclerAdapter.ViewHolder>(RssFeedDiffUtilCallback()) {

    private companion object {
        private const val GLIDE_THUMBNAIL_SIZE = 0.1f
    }

    inner class ViewHolder(private val itemBinding: RssRecyclerItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemView.setOnClickListener {
                getItem(adapterPosition)?.let { itemClickListener.onClick(it) }
            }
        }

        fun bind(rssItem: RssItem) {
            Glide.with(itemView.context).load(rssItem.imageUrl)
                .thumbnail(GLIDE_THUMBNAIL_SIZE)
                .into(itemBinding.rssImage)
            itemBinding.duration.text = rssItem.duration
            itemBinding.title.text = rssItem.title.substringBeforeLast("|")
            itemBinding.speaker.text = rssItem.speakers.joinToString(separator = " and ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RssRecyclerItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rssItem = getItem(position)
        rssItem?.let { holder.bind(it) }
    }
}

class RssFeedDiffUtilCallback : DiffUtil.ItemCallback<RssItem>() {
    override fun areItemsTheSame(oldItem: RssItem, newItem: RssItem): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: RssItem, newItem: RssItem): Boolean {
        return oldItem.title == newItem.title
    }
}

class RssRecyclerItemListener(val clickListener: (rssItem: RssItem) -> Unit) {
    fun onClick(rssItem: RssItem) = clickListener(rssItem)
}
