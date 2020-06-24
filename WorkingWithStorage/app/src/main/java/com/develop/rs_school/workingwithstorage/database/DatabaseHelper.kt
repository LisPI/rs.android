package com.develop.rs_school.workingwithstorage.database

import android.database.sqlite.SQLiteDatabase
import com.develop.rs_school.workingwithstorage.App
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

object DatabaseHelper: OrmLiteSqliteOpenHelper(App.instance, "friend.db", null, 1) {
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.createTableIfNotExists(connectionSource, Friend::class.java)
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