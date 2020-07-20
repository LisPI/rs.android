package com.develop.rs_school.thecatapi.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.develop.rs_school.thecatapi.network.Cat
import com.develop.rs_school.thecatapi.network.CatApi
import kotlinx.coroutines.*

class OverviewCatsViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init{
        getListOfCats()
    }

    private val _cats = MutableLiveData<List<Cat>>()

    val cats: LiveData<List<Cat>>
        get() = _cats


    private fun getListOfCats() {
        coroutineScope.launch {
            _cats.value = withContext(Dispatchers.IO){
                 CatApi.retrofitService.getCats()
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}