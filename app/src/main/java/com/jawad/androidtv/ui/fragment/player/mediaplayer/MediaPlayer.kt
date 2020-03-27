package com.jawad.androidtv.ui.fragment.player.mediaplayer

import android.net.Uri
import android.os.Handler
import android.view.View
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.*
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.EventLogger
import com.google.android.exoplayer2.util.Util
import com.jawad.androidtv.App
import com.jawad.androidtv.BuildConfig
import com.jawad.androidtv.ui.base.listeners.PlayerListener
import com.jawad.androidtv.util.Constants

/**
 * The class MediaPlayer
 *
 * @author  Jawad Usman
 * @web www.jawadusman.com
 * @version 1.0
 * @since 26 Mar 2020
 */


class MediaPlayer : Player.EventListener {

    /*Bandwidth estimate to data transfers*/
    private val BANDWIDTH_METER = DefaultBandwidthMeter()
    /*SimpleExoPlayer */
    private var player: SimpleExoPlayer? = null
    private var playWhenReady: Boolean = true
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0
    private var playerListener: PlayerListener? = null

    /**
     * Media player instance to Player view
     */
    fun getSimpleExoPlayer() = player

    /**
     * Gives the play back status to view
     * @param playerListener
     */
    fun setPlayerListener(playerListener: PlayerListener) {
        this.playerListener = playerListener
    }

    /**
     * Initiate the media player
     */
    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
            App.getInstance(),
            DefaultTrackSelector(), DefaultLoadControl()
        )
        player?.let {
            it.addListener(this)
            val mediaSource = buildMediaSource(Constants.MEDIA_URL)
            it.prepare(mediaSource, true, false)
            if (playbackPosition != 0L)
                it.seekTo(currentWindow, playbackPosition)
            it.playWhenReady = playWhenReady
        }
    }

    /**
     * Build Media Source
     * @param uris the URL to play the content
     * @return MediaSource
     */
    private fun buildMediaSource(url: String): MediaSource {
        val mediaUri: Uri = Uri.parse(url)
        return HlsMediaSource.Factory(buildDataSourceFactory(BANDWIDTH_METER))
            .createMediaSource(mediaUri)
    }

    /**
     * Build data source factory data source . factory.
     * Build http data source factory http data source . factory.
     * @param bandwidthMeter the bandwidth meter
     * @return the data source . factory
     */
    private fun buildDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter?): DataSource.Factory? {
        return DefaultDataSourceFactory(
            App.getInstance(), bandwidthMeter,
            buildHttpDataSourceFactory(bandwidthMeter)
        )
    }

    /**
     * Build http data source factory http data source . factory.
     * @param bandwidthMeter the bandwidth meter
     * @return the http data source . factory
     */
    private fun buildHttpDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter?): HttpDataSource.Factory? {
        val userAgent = Util.getUserAgent(App.getInstance(), BuildConfig.APPLICATION_ID)
        return DefaultHttpDataSourceFactory(userAgent, bandwidthMeter)
    }

    /**
     * onResume on Fragment
     */
    fun onStart() {
        initializePlayer()
    }

    /**
     * onResume on Fragment
     */
    fun onResume() {
        if (player == null)
            initializePlayer()
    }

    /**
     * onStop on Fragment
     */
    fun onStop() {
        releasePlayer()
    }

    /**
     * Clear all data from current instance
     * of player
     */
    private fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            playWhenReady = it.playWhenReady
            it.release()
            player = null
        }
    }

    /**
     * On player error function called when an error occurs while loading media data.
     * This indicate also playback has failed
     * @param error the error message
     */
    override fun onPlayerError(error: ExoPlaybackException?) {
        val message = when (error!!.type) {
            ExoPlaybackException.TYPE_SOURCE -> {
                "Data loading from a MediaSource"
            }
            ExoPlaybackException.TYPE_RENDERER -> {
                "TYPE RENDERER ${error.rendererException.message}"
            }
            ExoPlaybackException.TYPE_UNEXPECTED -> {
                "TYPE RENDERER ${error.unexpectedException.message}"
            }
            else -> {
                "TYPE UNKNOWN ${error.message}"
            }
        }
        playerListener?.onPlayerError(message)
    }

    /**
     * OnPlayerStateChanged called when all pending seek requests have been processed by the player.
     * This is guaranteed to happen after any necessary changes to the player state were reported to
     * @param playWhenReadyStatus is player ready to play
     * @param playbackState is the play status
     */
    override fun onPlayerStateChanged(playWhenReadyStatus: Boolean, playbackState: Int) {
        playerListener?.onPlayerStateChanged(playbackState)
        when (playbackState) {
            Player.STATE_READY -> {
                playerListener?.onPlayerError("")
            }
            Player.STATE_ENDED -> {
                player?.let {
                    currentWindow = 0
                    playbackPosition = 0
                    playWhenReady = it.playWhenReady
                }
            }
        }
    }

    /* Following function are not used at the movement */
    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
    }

    override fun onSeekProcessed() {
    }

    override fun onTracksChanged(
        trackGroups: TrackGroupArray?,
        trackSelections: TrackSelectionArray?
    ) {
    }

    override fun onLoadingChanged(isLoading: Boolean) {
    }

    override fun onPositionDiscontinuity(reason: Int) {
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
    }
}