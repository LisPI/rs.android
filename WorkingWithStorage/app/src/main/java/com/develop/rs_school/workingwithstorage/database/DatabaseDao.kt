package com.develop.rs_school.workingwithstorage.database

class DatabaseDao {

    private val dao = DatabaseHelper.getDao(Friend::class.java)

    fun add(friend: Friend) = dao.createOrUpdate(friend)

    fun update(friend: Friend) = dao.update(friend)

    fun delete(friend: Friend) = dao.delete(friend)

    fun queryForAll() = dao.queryForAll()

    fun sortQuery(columnName : String, descending : Boolean) = dao.queryBuilder().orderBy(columnName, descending).query()

    fun removeAll() {
        for (friend in queryForAll()) {
            dao.delete(friend)
        }
    }
}