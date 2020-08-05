package com.develop.rs_school.tedrssfeed

import android.annotation.SuppressLint
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.MediaController
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
    private lateinit var player : SimpleExoPlayer
    private lateinit var rssItem: RssItem
//TODO add backButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRssItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rssItem = intent.getParcelableExtra<RssItem>("item")!!

        val thumbnail = ThumbnailUtils.createVideoThumbnail(
            rssItem.videoUrl,
            MediaStore.Images.Thumbnails.MINI_KIND
        )
        val mediaController = MediaController(binding.videoView.context)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.apply {
            //background =
            setVideoURI(rssItem.videoUrl.toUri())
            setMediaController(mediaController)
        }

        //initializePlayer()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
/*
    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        binding.playerView.setPlayer(player)
        val media = getMediaSource(rssItem.videoUrl.toUri())

        player.playWhenReady = playWhenReady;
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(media, false, false);
    }

    private  fun getMediaSource(uri: Uri): ProgressiveMediaSource {
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
*/
}