package com.develop.rs_school.thecatapi.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL = "https://api.thecatapi.com/"

//TODO key to resource?
//TODO request param (page)
interface CatApiService {
    @Headers("x-api-key: fae8d3bc-0bd6-4cab-902e-c55793c100a6")
    @GET("v1/images/search?limit=10&page=10&order=Desc")
    suspend fun getCats(): List<Cat>
}

object CatApi {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: CatApiService = retrofit.create(CatApiService::class.java)
}