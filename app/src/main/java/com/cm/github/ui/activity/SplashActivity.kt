package com.cm.github.ui.activity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import com.cm.github.Constants
import com.cm.github.R
import com.cm.github.controller.AppController

class SplashActivity :BaseActivity() {

    lateinit var handler: Handler
    lateinit var mAppContext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mAppContext = applicationContext

        initView()
    }

    fun initView() {
        handler = Handler()
        handler.postDelayed({
            AppController().openGithubList(this@SplashActivity, true);
        }, Constants().SPLASH_TIME_OUT)
    }
}