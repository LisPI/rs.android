package com.develop.rs_school.tedrssfeed

import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO add parser. It is just for fun
        val jsonString = this.assets.open("data.json").bufferedReader().use { it.readText() }
        val json = JSONObject(jsonString)
        val itemsArray = json.getJSONObject("channel").getJSONArray("item")
        findViewById<TextView>(R.id.text).text = itemsArray.getJSONObject(0).getString("title")

//        for test videoView
//        val vv = findViewById<VideoView>(R.id.videoView)
//        vv.setVideoPath(itemsArray.getJSONObject(0).getJSONObject("enclosure").getString("url"))
//        vv.setMediaController(MediaController(this));
//        vv.requestFocus()
//        vv.start()

        //https://www.ted.com/themes/rss/id
    }
}
