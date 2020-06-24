package com.develop.rs_school.workingwithstorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.develop.rs_school.workingwithstorage.database.DatabaseDao
import com.develop.rs_school.workingwithstorage.database.DatabaseHelper
import com.develop.rs_school.workingwithstorage.database.Friend
import com.develop.rs_school.workingwithstorage.databinding.ActivityMainBinding
import com.develop.rs_school.workingwithstorage.settings.SettingsActivity
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val dao = DatabaseDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "DBContent"

        binding.floatingActionButton.setOnClickListener {
            //FIXME for test db working
            dao.add(Friend(name = "alex", city = "minsk"))
            Toast.makeText(this, dao.queryForAll()[1].DOB.toString(), Toast.LENGTH_SHORT).show()
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
        //FIXME
        DatabaseHelper.close()
    }

}