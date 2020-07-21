package com.develop.rs_school.thecatapi.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.develop.rs_school.thecatapi.R
import com.develop.rs_school.thecatapi.databinding.OverviewCatsFragmentBinding
import com.develop.rs_school.thecatapi.network.CatApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OverviewCatsFragment : Fragment() {

    private var _binding: OverviewCatsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OverviewCatsViewModel

    //TODO safeARGS to send param (Cat)
    //TODO RecyclerViewPreloader CLIDE

    //TODO  to VM, observable later
    private val adapter = CatRecyclerAdapter(CatRecyclerItemListener {
        findNavController().navigate(OverviewCatsFragmentDirections.actionOverviewCatsFragmentToDetailCatFragment(it))
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = OverviewCatsFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(OverviewCatsViewModel::class.java)

        binding.catRecycler.adapter = adapter

        viewModel.cats.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}