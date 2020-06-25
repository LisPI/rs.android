package com.develop.rs_school.workingwithstorage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.develop.rs_school.workingwithstorage.database.DatabaseDao
import com.develop.rs_school.workingwithstorage.database.Friend
import com.develop.rs_school.workingwithstorage.databinding.ActivityMainBinding
import com.develop.rs_school.workingwithstorage.settings.SettingsActivity
import com.j256.ormlite.dao.Dao
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val dao = DatabaseDao()
    private val adapter = FriendRecyclerAdapter()

    private var sortBy : String? = null
    private var isSortDesc = false

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = getString(R.string.FriendsMainActivityTitle)

        binding.floatingActionButton.setOnClickListener {
            openAddItemActivity.launch()
        }

        binding.friendRecycler.layoutManager = LinearLayoutManager(this)
        binding.friendRecycler.adapter = adapter


        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        sortBy = prefs.getString(getString(R.string.sortByPrefKey), null)
        isSortDesc = prefs.getBoolean(getString(R.string.isDescSortPrefKey), false)
        uiScope.launch {
            adapter.submitList(getFriendsFromDatabase(sortBy, isSortDesc))
        }
    }

    override fun onResume() {
        super.onResume()

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if((sortBy != prefs.getString(getString(R.string.sortByPrefKey), null)) || (isSortDesc != prefs.getBoolean(getString(R.string.isDescSortPrefKey), false))) {
            sortBy = prefs.getString(getString(R.string.sortByPrefKey), null)
            isSortDesc = prefs.getBoolean(getString(R.string.isDescSortPrefKey), false)

            //adapter.submitList(listOf())

            uiScope.launch {
                adapter.submitList(getFriendsFromDatabase(sortBy, isSortDesc))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settingsActivityMenuItem -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelJob.cancel()
        //FIXME
        //DatabaseHelper.close()
    }

    private val openAddItemActivity =
        registerForActivityResult(AddItemActivityResultContract()) { newFriend ->
            if (newFriend != null) {
                uiScope.launch {
                    val result = insertFriendToDb(newFriend)
                    if(result.isCreated){
                        adapter.submitList(getFriendsFromDatabase(sortBy, isSortDesc))
                    }
                }
            }
        }

    private suspend fun insertFriendToDb(newFriend : Friend) : Dao.CreateOrUpdateStatus{
        return withContext(Dispatchers.IO) {
            dao.add(newFriend)
        }
    }


    private suspend fun getFriendsFromDatabase(sortBy : String?, isSortDesc : Boolean): List<Friend> {
        return withContext(Dispatchers.IO) {
            dao.queryForAll()
            if(sortBy.isNullOrEmpty())
                dao.queryForAll()
            else
                dao.sortQuery(sortBy, isSortDesc)
        }
    }

}