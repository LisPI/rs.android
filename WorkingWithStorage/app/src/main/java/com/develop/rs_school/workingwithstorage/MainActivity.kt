package com.develop.rs_school.workingwithstorage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.develop.rs_school.workingwithstorage.database.DatabaseDao
import com.develop.rs_school.workingwithstorage.database.DatabaseHelper
import com.develop.rs_school.workingwithstorage.databinding.ActivityMainBinding
import com.develop.rs_school.workingwithstorage.settings.SettingsActivity
import com.j256.ormlite.android.apptools.OpenHelperManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val dao = DatabaseDao()
    private lateinit var adapter : FriendRecyclerAdapter

    private val openAddItemActivity =
        registerForActivityResult(AddItemActivityResultContract()) { result ->
            if (result != null) {
                Toast.makeText(this, result.name, Toast.LENGTH_SHORT).show()
                //FIXME to Coroutine
                //FIXME update recycler after
                dao.add(result)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Friends"

        binding.floatingActionButton.setOnClickListener {
            openAddItemActivity.launch()
        }

        binding.friendRecycler.layoutManager = LinearLayoutManager(this)
        adapter = FriendRecyclerAdapter()
        binding.friendRecycler.adapter = adapter

        //FIXME to Coroutine
        adapter.submitList(dao.queryForAll())
    }

    override fun onResume() {
        super.onResume()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val sortBy = prefs.getString("sortByPreference", null)
        val isSortDesc = prefs.getBoolean("descendingCheckBox", false)

        if(sortBy.isNullOrEmpty())
            adapter.submitList(dao.queryForAll())
        else
            adapter.submitList(dao.sortQuery(sortBy, isSortDesc))
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
        //FIXME
        //DatabaseHelper.close()
    }

}