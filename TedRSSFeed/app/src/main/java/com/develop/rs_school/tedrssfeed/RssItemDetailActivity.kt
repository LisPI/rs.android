package com.develop.rs_school.tedrssfeed

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.develop.rs_school.tedrssfeed.databinding.ActivityRssItemDetailBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class RssItemDetailActivity : AppCompatActivity() {

    private companion object {
        private const val ASPECT_RATIO = 16f / 9f
        private const val API_VERSION_ON_STOP_GUARANTEE = 24
    }

    private lateinit var binding: ActivityRssItemDetailBinding
    private lateinit var player: SimpleExoPlayer
    private lateinit var rssItem: RssItem

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var isFullscreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRssItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // FIXME avoid !!
        val extra = intent.getParcelableExtra<RssItem>(getString(R.string.rss_item_intent_key))
        if (extra == null) {
            finish()
            return
        } else rssItem = extra

        binding.fullDescription.text = rssItem.description
        binding.title.text = getTitle(rssItem.title)
        binding.speaker.text = getSpeakers(rssItem.speakers)
        Glide.with(this).load(rssItem.imageUrl)
            .into(binding.previewImage)

        binding.playerFrame.setAspectRatio(ASPECT_RATIO)

        binding.playButton.setOnClickListener {
            binding.playerView.visibility = View.VISIBLE
            binding.previewImage.visibility = View.GONE
            binding.playButton.visibility = View.GONE

            player.playWhenReady = true
        }

        val fullscreenButton = binding.playerView.findViewById<ImageView>(R.id.exo_fullscreen_icon)
        fullscreenButton.setOnClickListener {
            if (isFullscreen) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                supportActionBar?.show()
                isFullscreen = false
            } else {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                supportActionBar?.hide()
                isFullscreen = true
            }
        }
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
            DefaultDataSourceFactory(this, getString(R.string.app_name))
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= API_VERSION_ON_STOP_GUARANTEE) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < API_VERSION_ON_STOP_GUARANTEE) {
            initializePlayer()
        }
        if (isFullscreen) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
            supportActionBar?.hide()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < API_VERSION_ON_STOP_GUARANTEE) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= API_VERSION_ON_STOP_GUARANTEE) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        playWhenReady = player.playWhenReady
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        player.release()
    }
}
