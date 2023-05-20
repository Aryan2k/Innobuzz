package com.example.innobuzz.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.innobuzz.R
import com.example.innobuzz.databinding.LayoutProgressDialogBinding
import com.google.android.material.snackbar.Snackbar

object FunctionUtils {
    fun animateView(view: View, duration: Long = 500, repeat: Int = 0, techniques: Techniques = Techniques.Shake) {
        YoYo.with(techniques).duration(duration).repeat(repeat).playOn(view)
    }

    fun focusScreen(view: View) {
        view.setOnApplyWindowInsetsListener { _, windowInsets ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val imeHeight = windowInsets.getInsets(WindowInsets.Type.ime()).bottom
                view.setPadding(0, 0, 0, imeHeight)
            }
            windowInsets
        }
    }

    fun navigate(view: View, action: NavDirections? = null, id: Int? = null) {
        if (action == null && id != null)
            Navigation.findNavController(view).navigate(id)
        else if (id == null && action != null)
            Navigation.findNavController(view).navigate(action)
    }

    fun setUpDialog(message: String, context: Context): Dialog {
        val dialog = Dialog(context, R.style.CustomDialogTheme).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setCancelable(false)
            val dialogBinding = LayoutProgressDialogBinding.inflate(layoutInflater)
            this.setContentView(dialogBinding.root)
            val width = (context.resources.displayMetrics.widthPixels * 0.80).toInt()
            this.window?.setLayout(width, ActionBar.LayoutParams.WRAP_CONTENT)
            dialogBinding.dialogMessageTxt.text = message
        }
        return dialog
    }
}