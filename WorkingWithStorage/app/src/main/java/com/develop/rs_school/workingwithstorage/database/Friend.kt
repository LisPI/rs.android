package com.develop.rs_school.workingwithstorage.database

import android.os.Parcelable
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@DatabaseTable(tableName = "table")
data class Friend(

    @DatabaseField(generatedId = true)
    var id: Int? = null,

    @DatabaseField
    var name: String = "",

    @DatabaseField
    var city: String = "",

    @DatabaseField
    var DOB : Date = Date()
) : Parcelable
