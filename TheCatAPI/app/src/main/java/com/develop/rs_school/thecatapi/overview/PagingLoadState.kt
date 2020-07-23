package com.develop.rs_school.thecatapi.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.develop.rs_school.thecatapi.R
import com.develop.rs_school.thecatapi.databinding.PagingLoadStateBinding

class PagingLoadStateHolder(
    private val binding: PagingLoadStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PagingLoadStateHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.paging_load_state, parent, false)
            val binding = PagingLoadStateBinding.bind(view)
            return PagingLoadStateHolder(binding, retry)
        }
    }
}

class PagingLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<PagingLoadStateHolder>() {
    override fun onBindViewHolder(holder: PagingLoadStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PagingLoadStateHolder {
        return PagingLoadStateHolder.create(parent, retry)
    }
}
