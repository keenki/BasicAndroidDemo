package me.keen.mvpdemo.base

import android.content.Context
import androidx.multidex.MultiDex
import me.keen.baselibs.app.BaseApplication
import kotlin.properties.Delegates

/**
 * @author xiekeen
 * @date 2019-09-24
 * @desc App
 */
class App : BaseApplication() {

    companion object {
        var instance: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}