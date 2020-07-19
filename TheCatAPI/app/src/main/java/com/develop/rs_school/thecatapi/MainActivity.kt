package com.develop.rs_school.thecatapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.develop.rs_school.thecatapi.databinding.ActivityMainBinding
import com.develop.rs_school.thecatapi.network.CatApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main )
        coroutineScope.launch {
            val list = CatApi.retrofitService.getCats()
            Glide.with(this@MainActivity).load(list[0].imageUrl).into(binding.imageView)
        }
    }
}