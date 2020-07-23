package com.develop.rs_school.thecatapi.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.develop.rs_school.thecatapi.databinding.OverviewCatsFragmentBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class OverviewCatsFragment : Fragment() {

    private var _binding: OverviewCatsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OverviewCatsViewModel

    //TODO RecyclerViewPreloader CLIDE
    //pagination
    //save to Gallery
    //detect ktlint

    //opt-----------------
    //gradle to kotlin
    //spek2 + mockk

    //TODO  to VM, observable later - event
    private val adapter = CatRecyclerAdapter(CatRecyclerItemListener {
        findNavController().navigate(
            OverviewCatsFragmentDirections.actionOverviewCatsFragmentToDetailCatFragment(it)
        )
    })

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = OverviewCatsFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(OverviewCatsViewModel::class.java)

        initRecyclerAdapter()

        lifecycleScope.launch {
            viewModel.cats.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        binding.retryButton.setOnClickListener { adapter.retry() }

        return binding.root
    }

    private fun initRecyclerAdapter() {
        binding.catRecycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter { adapter.retry() },
            footer = PagingLoadStateAdapter { adapter.retry() }
        )
        //TODO add image no internet
        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.catRecycler.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}