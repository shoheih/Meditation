package net.minpro.meditation.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import net.minpro.meditation.R
import net.minpro.meditation.model.UserSettingsRepository
import net.minpro.meditation.util.NO_BGM

class MusicService : Service() {

    private val binder: IBinder = MusicBinder()

    inner class MusicBinder: Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }

    private var bellsPlayer: SimpleExoPlayer? = null
    private var bgmPlayer: SimpleExoPlayer? = null

    private var rawResourceDataSourceBell: RawResourceDataSource? = null
    private var rawResourceDataSourceBgm: RawResourceDataSource? = null

    override fun onBind(intent: Intent): IBinder {
        bellsPlayer = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        bgmPlayer = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        rawResourceDataSourceBgm = RawResourceDataSource(this)
        rawResourceDataSourceBell = RawResourceDataSource(this)

        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {

        return super.onUnbind(intent)
    }

    fun startBgm() {
        val userSettingsRepository = UserSettingsRepository()
        val bellsSoundId = when(userSettingsRepository.loadUserSettings().levelId) {
            0 -> R.raw.bells_easy
            1 -> R.raw.bells_normal
            2 -> R.raw.bells_mid
            3 -> R.raw.bells_advanced
            else -> 0
        }

        val themeSoundId = userSettingsRepository.loadUserSettings().themeSoundId
        bellsPlayer?.let { startSound(bellsPlayer!!, bellsSoundId, rawResourceDataSourceBell!!) }
        if (themeSoundId != NO_BGM) bgmPlayer?.let { startSound(bgmPlayer!!, themeSoundId, rawResourceDataSourceBgm!!) }
    }

    private fun startSound(exoPlayer: SimpleExoPlayer, soundId: Int, rawResourceDataSource: RawResourceDataSource) {
        val dataSourceUri = RawResourceDataSource.buildRawResourceUri(soundId)
    }

    fun stopBgm() {

    }

    fun setVolume() {

    }

    fun ringFinalGong() {

    }

}
