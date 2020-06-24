package com.develop.rs_school.workingwithstorage.database

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "table")
data class Friend(

    @DatabaseField(generatedId = true)
    var id: Int? = null,

    @DatabaseField
    var name: String = "",

    @DatabaseField
    var city: String = "",

    //FIXME date format
    @DatabaseField
    var DOB : Date = Date()
)
