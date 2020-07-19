package com.develop.rs_school.thecatapi.network

import com.squareup.moshi.Json

data class Cat(val id: String, @Json(name = "url") val imageUrl: String)
