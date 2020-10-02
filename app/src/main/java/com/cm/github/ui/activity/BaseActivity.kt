package com.cm.github.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.cm.github.R
import com.cm.github.controller.GlobalController
import com.cm.github.toolbox.DialogBuilder

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        lateinit var alertDialog: AlertDialog
        var mLastClickTime: Long = 0
    }

    override fun onPause() {
        closeLoading()
        super.onPause()
    }

    fun closeKeyboard() {
        val v = currentFocus
        if (v is EditText) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun showLoading(ctx : Context) {
        val dialog = AlertDialog.Builder(ctx)
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View
        v = inflater.inflate(R.layout.dialog_loading, null)
        dialog.setView(v)

        alertDialog = dialog.create()
        alertDialog.setCancelable(false)
        alertDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    fun closeLoading() {
        try {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss()
            }
        } catch (e: IllegalArgumentException) {
            // Handle or log or ignore
        } catch (e: Exception) {
            // Handle or log or ignore
        }
    }

    fun showMessage(title: String, content: String, ctx: Context) {
        DialogBuilder().show(title, content, ctx)
    }

    fun showErrorInternet(ctx: Context) {
        DialogBuilder().show(
            getString(R.string.error_internet),
            GlobalController().getString(R.string.error_check_internet),
            ctx
        )
    }
}