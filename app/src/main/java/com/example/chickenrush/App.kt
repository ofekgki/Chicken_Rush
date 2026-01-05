package com.example.chickenrush

import android.app.Application
import com.example.chickenrush.utilities.BackgroundMusicPlayer
import com.example.chickenrush.utilities.SharedPreferencesManager
import com.example.chickenrush.utilities.SignalManager

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesManager.init(this)
        SignalManager.init(this)
        BackgroundMusicPlayer.init(this)
        BackgroundMusicPlayer.getInstance().setResourceId(R.raw.farmanimals)

    }

    override fun onTerminate() {
        super.onTerminate()
        BackgroundMusicPlayer.getInstance().stopMusic()
    }
}