package xyz.teamgravity.notepad.injection.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import xyz.teamgravity.notepad.BuildConfig
import xyz.teamgravity.pin_lock_compose.PinManager
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var tree: Timber.DebugTree

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(tree)
        PinManager.initialize(this)
    }
}