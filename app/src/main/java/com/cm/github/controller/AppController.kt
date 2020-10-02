package com.cm.github.controller

import android.app.Activity
import android.content.Intent
import com.cm.github.R
import com.cm.github.CMApplication
import com.cm.github.ui.activity.GithubListActivity
import com.cm.github.ui.activity.GithubSearchListActivity

class AppController {

    fun openGithubList(activity: Activity?, close: Boolean) {
        if (activity == null) {
            return
        }

        val intent = Intent(CMApplication().getAppContext(), GithubListActivity::class.java)
        activity.startActivity(intent)
        if (close) {
            activity.finish()
        }
    }

    fun openGithubSearchList(activity: Activity?, close: Boolean) {
        if (activity == null) {
            return
        }

        val intent = Intent(CMApplication().getAppContext(), GithubSearchListActivity::class.java)
        activity.startActivity(intent)
        activity.overridePendingTransition(0, R.anim.fade_out)
        if (close) {
            activity.finish()
        }
    }
}