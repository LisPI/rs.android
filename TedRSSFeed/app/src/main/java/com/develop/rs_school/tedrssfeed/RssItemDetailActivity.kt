package com.develop.rs_school.tedrssfeed

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.develop.rs_school.tedrssfeed.databinding.ActivityRssItemDetailBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class RssItemDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRssItemDetailBinding
    private lateinit var player: SimpleExoPlayer
    private lateinit var rssItem: RssItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRssItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rssItem = intent.getParcelableExtra<RssItem>("item")!!

        binding.fullDescription.text = rssItem.description
        binding.title.text = getTitle(rssItem.title)
        binding.speaker.text = getSpeakers(rssItem.speakers)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initializePlayer() {

        player = SimpleExoPlayer.Builder(this).build()

        binding.playerView.player = player

        val media = getMediaSource(rssItem.videoUrl.toUri())

        player.seekTo(currentWindow, playbackPosition)
        player.prepare(media, false, false)
    }

    private fun getMediaSource(uri: Uri): ProgressiveMediaSource {
        val dataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(this, "tedrssfeed")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        // hideSystemUi()
        if (Util.SDK_INT < 24) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    private fun releasePlayer() {
        playWhenReady = player.playWhenReady
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        player.release()
    }

//    @SuppressLint("InlinedApi")
//    private fun hideSystemUi() {
//        binding.playerView.systemUiVisibility = (
//                //View.SYSTEM_UI_FLAG_LOW_PROFILE or
//                View.SYSTEM_UI_FLAG_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//
//
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                )
//    }
//
//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        if (hasFocus) hideSystemUi()
//    }
}
