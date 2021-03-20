package com.develop.rs_school.thecatapi.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cat(val id: String, @Json(name = "url") val imageUrl: String) : Parcelable
