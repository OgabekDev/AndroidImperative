package dev.ogabek.android_imperative

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.ogabek.android_imperative.utils.Logger

@HiltAndroidApp
class ImperativeApplication: Application() {

    private val TAG = this::class.java.simpleName

    override fun onCreate() {
        Logger.d(TAG, "App onCreate()")
        super.onCreate()
    }

}