package com.develop.rs_school.workingwithstorage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.develop.rs_school.workingwithstorage.database.Friend
import com.develop.rs_school.workingwithstorage.databinding.ActivityAddItemBinding


class AddItemActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Add friend"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("Friend", Friend(name = binding.nameEt.text.toString(), city = binding.cityEt.text.toString()))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}