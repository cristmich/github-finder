package com.cm.github.toolbox

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.cm.github.R
import kotlinx.android.synthetic.main.dialog_alert.view.*

class DialogBuilder {
    private val nullParent: ViewGroup? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context

        private var mTitle: String? = null
        private var mContent: String? = null
        private var mButtonText: String? = null
        private var mPositiveButtonText: String? = null
        private var mNegativeButtonText: String? = null
    }

    fun show(title: String, content: String, context: Context) {
        mContext = context
        mTitle = title
        mContent = content
        mButtonText = context.getString(R.string.text_close)
        show()
    }

    fun show() {
        //Inflate the dialog as custom view
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_alert, null)

        //AlertDialogBuilder
        val alertDialog = AlertDialog.Builder(mContext).setView(view)
        view.tvTitle.text = mTitle
        view.tvContent.text = mContent
        view.btnClose.text = mButtonText
        val instance = alertDialog.show()
        view.btnClose.setOnClickListener(View.OnClickListener { instance.dismiss() })
    }
}