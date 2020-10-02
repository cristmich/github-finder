package com.cm.github

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.google.gson.Gson

class CMApplication : MultiDexApplication() {
    var gson = Gson()

    companion object {
        lateinit var mAppContext : Context
    }

    override fun onCreate() {
        super.onCreate()

        mAppContext = applicationContext
    }

    fun getAppContext(): Context {
        return mAppContext
    }
}
