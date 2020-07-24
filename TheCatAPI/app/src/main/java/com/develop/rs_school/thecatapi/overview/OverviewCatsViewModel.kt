package com.develop.rs_school.thecatapi.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.develop.rs_school.thecatapi.network.Cat

class OverviewCatsViewModel : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 20
    }

    val catsFlow =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE)) { CatPagingSource() }
            .flow.cachedIn(viewModelScope)

    private val _navigateToDetailCat = MutableLiveData<Cat>()
    val navigateToDetailCat
        get() = _navigateToDetailCat

    fun onCatClicked(cat: Cat) {
        _navigateToDetailCat.value = cat
    }

    fun onDetailCatNavigated() {
        _navigateToDetailCat.value = null
    }
}
