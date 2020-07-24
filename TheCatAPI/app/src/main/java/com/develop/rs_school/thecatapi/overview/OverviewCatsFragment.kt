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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

class OverviewCatsFragment : Fragment() {

    private var _binding: OverviewCatsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OverviewCatsViewModel

    // TODO  to VM, observable later - event
    private val adapter = CatRecyclerAdapter(CatRecyclerItemListener {
        findNavController().navigate(
            OverviewCatsFragmentDirections.actionOverviewCatsFragmentToDetailCatFragment(it)
        )
    })

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = OverviewCatsFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(OverviewCatsViewModel::class.java)

        initRecyclerAdapter()

        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
        binding.retryButton.setOnClickListener { adapter.retry() }

        lifecycleScope.launchWhenCreated {
            viewModel.catsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        return binding.root
    }

    private fun initRecyclerAdapter() {
        binding.catRecycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter { adapter.retry() },
            footer = PagingLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.Error) {
                binding.retryButton.isVisible = true
                binding.errorImage.isVisible = true
                binding.catRecycler.isVisible = false
            }

            if (loadState.source.refresh is LoadState.NotLoading) {
                binding.retryButton.isVisible = false
                binding.errorImage.isVisible = false
                binding.catRecycler.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
