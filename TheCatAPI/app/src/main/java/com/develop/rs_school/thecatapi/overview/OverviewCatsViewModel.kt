package com.develop.rs_school.thecatapi.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.develop.rs_school.thecatapi.SingleLiveEvent
import com.develop.rs_school.thecatapi.network.Cat

class OverviewCatsViewModel : ViewModel() {

    private companion object {
        private const val PAGE_SIZE = 20
    }

    val catsFlow =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE)) { CatPagingSource() }
            .flow.cachedIn(viewModelScope)

    private val _navigateToDetailCat = SingleLiveEvent<Cat>()
    val navigateToDetailCat: SingleLiveEvent<Cat>
        get() = _navigateToDetailCat

    fun onCatClicked(cat: Cat) {
        _navigateToDetailCat.value = cat
    }
}
