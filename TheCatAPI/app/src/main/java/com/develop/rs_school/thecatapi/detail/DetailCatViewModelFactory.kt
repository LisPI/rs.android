package com.develop.rs_school.thecatapi.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.develop.rs_school.thecatapi.network.Cat

class DetailCatViewModelFactory( private val cat: Cat) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailCatViewModel::class.java)) {
            return DetailCatViewModel(cat) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}