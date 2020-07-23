package com.develop.rs_school.thecatapi.overview

import androidx.paging.PagingSource
import com.develop.rs_school.thecatapi.network.Cat
import com.develop.rs_school.thecatapi.network.CatApi

class CatPagingSource(): PagingSource<Int, Cat>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        try{
            val page = params.key ?: 1
            val size = params.loadSize
            val response = CatApi.retrofitService.getCats(page, size)
            return LoadResult.Page(data = response,
                prevKey = if(page==0) null else page - 1,
                nextKey = if(response.isEmpty()) null else page + 1)
        }
        catch(e: Exception){
            return LoadResult.Error(e)
        }
    }
}