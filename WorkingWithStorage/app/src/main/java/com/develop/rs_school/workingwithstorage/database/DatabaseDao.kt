package com.develop.rs_school.workingwithstorage.database

import com.j256.ormlite.dao.Dao

class DatabaseDao {

    private val dao: Dao<Friend, Int> = DatabaseHelper.getDao(Friend::class.java)

    fun add(friend: Friend) = dao.createOrUpdate(friend)

    fun deleteById(friendId: Int) = dao.deleteById(friendId)

    fun queryForAll() = dao.queryForAll()

    fun sortQuery(columnName: String, descending: Boolean) =
        dao.queryBuilder().orderBy(columnName, descending).query()

}