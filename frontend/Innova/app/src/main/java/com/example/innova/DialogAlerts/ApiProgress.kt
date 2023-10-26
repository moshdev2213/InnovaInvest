package com.example.innova.DialogAlerts

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import com.example.innova.R

class ApiProgress(
    private val context: Context
) {
    var dialog = Dialog(context)

    fun startProgressLoader(){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.api_loader)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

        dialog.show()
    }
    fun dismissProgressLoader(){
        dialog.dismiss()
    }
}