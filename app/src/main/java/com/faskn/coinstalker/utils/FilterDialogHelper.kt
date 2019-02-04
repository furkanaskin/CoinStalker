package com.faskn.coinstalker.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import com.faskn.coinstalker.R

class FilterDialogHelper(context: Context) : BaseDialogHelper() {
    //  dialog view
    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.filter_dialog, null)
    }

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)

    //  done icon
    private val doneIcon: ImageView by lazy {
        dialogView.findViewById<ImageView>(R.id.done_icon)
    }

    //  Radio Button
    private val radioButtonUSD: RadioButton by lazy {
        dialogView.findViewById<RadioButton>(R.id.radioUSD)
    }

    //  close icon
    private val closeIcon: ImageView by lazy {
        dialogView.findViewById<ImageView>(R.id.close_icon)
    }


    fun closeIconClickListener(func: (() -> Unit)? = null) =
        with(closeIcon) {
            setClickListenerToDialogIcon(func)
        }

    fun doneIconClickListener(func: (() -> Unit)? = null) =
        with(doneIcon) {
            setClickListenerToDialogIcon(func)
        }

    fun radioButtonSelectedListener(func: (() -> Unit)? = null) =
        with(radioButtonUSD) {
            radioButtonSetOnClickListenerToDialogIcon(func)
        }

    //  view click listener as extension function
    private fun View.setClickListenerToDialogIcon(func: (() -> Unit)?) =
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }

    private fun View.radioButtonSetOnClickListenerToDialogIcon(func: (() -> Unit)?) =
        setOnClickListener {
            func?.invoke()
        }
}


