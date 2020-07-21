package com.develop.rs_school.thecatapi.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.develop.rs_school.thecatapi.network.Cat
import com.develop.rs_school.thecatapi.network.CatApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class ConnectionStatus { PENDING, ERROR, SUCCESS }

class OverviewCatsViewModel : ViewModel() {


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getListOfCats()
    }

    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>>
        get() = _cats

    private val _connectionStatus = MutableLiveData<ConnectionStatus>()
    val connectionStatus: LiveData<ConnectionStatus>
        get() = _connectionStatus


    private fun getListOfCats() {
        coroutineScope.launch {
            try {
                _connectionStatus.value = ConnectionStatus.PENDING
                _cats.value = withContext(Dispatchers.IO) {
                    CatApi.retrofitService.getCats()
                }
                _connectionStatus.value = ConnectionStatus.SUCCESS
            } catch (e: Exception) {
                _connectionStatus.value = ConnectionStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}