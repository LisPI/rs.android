package com.develop.rs_school.workingwithstorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.launch
import com.develop.rs_school.workingwithstorage.database.DatabaseDao
import com.develop.rs_school.workingwithstorage.database.DatabaseHelper
import com.develop.rs_school.workingwithstorage.database.Friend
import com.develop.rs_school.workingwithstorage.databinding.ActivityMainBinding
import com.develop.rs_school.workingwithstorage.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val dao = DatabaseDao()

    private val openAddItemActivity =
        registerForActivityResult(AddItemActivityResultContract()) { result ->
            if (result != null) {
                Toast.makeText(this, result.name, Toast.LENGTH_SHORT).show()
                dao.add(result)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "DBContent"

        binding.floatingActionButton.setOnClickListener {
            openAddItemActivity.launch()
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