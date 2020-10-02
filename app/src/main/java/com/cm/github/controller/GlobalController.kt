package com.cm.github.controller

import com.cm.github.CMApplication
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener

class GlobalController {

    fun getString(resourceId: Int): String {
        return CMApplication().getAppContext().getString(resourceId)
    }

    fun isJSONObject(data: String): Boolean {
        try {
            val json = JSONTokener(data).nextValue()
            if (json is JSONObject) {
                return true
            }
        } catch (ex: JSONException) {
            ex.printStackTrace()
        }

        return false
    }

    fun isNotNull(value: String?): Boolean {
        return if (value == null) {
            false
        } else {
            true
        }
    }
}