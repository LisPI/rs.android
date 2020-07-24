package com.develop.rs_school.thecatapi.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class OverviewCatsViewModel : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 20
    }

    val catsFlow =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE)) { CatPagingSource() }
            .flow.cachedIn(viewModelScope)
}
