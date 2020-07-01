package com.develop.rs_school.workingwithstorage.database

import android.database.sqlite.SQLiteDatabase
import com.develop.rs_school.workingwithstorage.App
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.util.*

object DatabaseHelper : OrmLiteSqliteOpenHelper(App.instance, "friend.db", null, 1) {
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTableIfNotExists(connectionSource, Friend::class.java)

        //mock data
        val cities = arrayOf("Minsk", "Berlin", "Barcelona")
        val names = arrayOf("Paul", "Alex", "Kate")
        val cldr = Calendar.getInstance()

        for (i in 1..10) {
            cldr.set(2010 - i, i, 2 + i)
            getDao(Friend::class.java).createOrUpdate(
                Friend(
                    name = names.random(),
                    city = cities.random(),
                    DOB = cldr.time
                )
            )
        }
    }

    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TableUtils.dropTable<Friend, Any>(connectionSource, Friend::class.java, true)
        onCreate(database, connectionSource)
    }
}