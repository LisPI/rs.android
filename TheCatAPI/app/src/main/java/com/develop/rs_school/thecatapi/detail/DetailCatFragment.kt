package com.develop.rs_school.thecatapi.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.develop.rs_school.thecatapi.databinding.DetailCatFragmentBinding

class DetailCatFragment : Fragment() {

    private var _binding: DetailCatFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailCatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DetailCatFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DetailCatViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}