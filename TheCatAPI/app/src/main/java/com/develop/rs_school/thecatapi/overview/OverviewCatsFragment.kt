package com.develop.rs_school.thecatapi.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.develop.rs_school.thecatapi.R
import com.develop.rs_school.thecatapi.databinding.OverviewCatsFragmentBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = OverviewCatsFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(OverviewCatsViewModel::class.java)

        binding.catRecycler.adapter = adapter

        viewModel.cats.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.connectionStatus.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                when (it) {
                    ConnectionStatus.PENDING -> {
                        binding.errorImage.visibility = View.VISIBLE
                        binding.errorImage.setImageResource(R.drawable.loading_animation)
                    }
                    ConnectionStatus.ERROR -> {
                        binding.errorImage.visibility = View.VISIBLE
                        binding.errorImage.setImageResource(R.drawable.connection_error)
                    }
                    ConnectionStatus.SUCCESS -> {
                        binding.errorImage.visibility = View.GONE
                    }
                }
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}