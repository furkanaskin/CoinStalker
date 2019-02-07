package com.faskn.coinstalker.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.faskn.coinstalker.R

class FilterDialogHelper(context: Context) : BaseDialogHelper() {

    //  shared pref.
    private val sharedPref by lazy {
        context.getSharedPreferences(
            SharedPrefKey.PrivateSharedPref.toString(),
            Context.MODE_PRIVATE
        )
    }

    //  dialog view
    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.filter_dialog, null)
    }

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)

    //  exit button
    private val exitButton: ImageView by lazy {
        dialogView.findViewById<ImageView>(R.id.button_exit)
    }

    //  Coin Type Radio Group
    private val radioGroupCoinType: RadioGroup by lazy {
        dialogView.findViewById<RadioGroup>(R.id.radioGroupCoinType)
    }

    //  Sorting Type Radio Group
    private val radioGroupSortType: RadioGroup by lazy {
        dialogView.findViewById<RadioGroup>(R.id.radioGroupSortType)
    }

    fun exitButtonClickListener(func: (() -> Unit)? = null) =
        with(exitButton) {
            setClickListenerToDialogIcon(func)
        }


    fun radioGroupClickListener(func: (() -> Unit)? = null) {

        with(radioGroupCoinType) {
            setCoinRadioGroupClickListener(func)
        }

        with(radioGroupSortType) {
            setSortingRadioGroupClickListener(func)
        }
    }

    fun retrieveChoices() {
        val lastCheckedCoinType =
            sharedPref.getInt(SharedPrefKey.CheckedBaseButtonID.toString(), -1)
        val lastCheckedSortType =
            sharedPref.getInt(SharedPrefKey.CheckedSortFilterButtonID.toString(), -1)
        if (lastCheckedCoinType > 0) {
            radioGroupCoinType.check(lastCheckedCoinType)
        }

        if (lastCheckedSortType > 0) {
            radioGroupSortType.check(lastCheckedSortType)
        }
    }

    //  view click listener as extension function
    private fun View.setClickListenerToDialogIcon(func: (() -> Unit)?) =
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }


    private fun RadioGroup.setCoinRadioGroupClickListener(func: (() -> Unit)?) =
        setOnCheckedChangeListener { group, checkedId ->
            func?.invoke()
            val checkedItem = group.findViewById<RadioButton>(checkedId)
            sharedPref.edit().setBase(checkedItem.text as String)
            sharedPref.edit().setCheckedBaseButtonID(checkedId)
        }

    private fun RadioGroup.setSortingRadioGroupClickListener(func: (() -> Unit)?) =
        setOnCheckedChangeListener { group, checkedId ->
            func?.invoke()
            val checkedItem = group.findViewById<RadioButton>(checkedId)
            sharedPref.edit().setFilter(checkedItem.text as String)
            sharedPref.edit().setCheckedSortingButtonID(checkedId)
        }

    private fun SharedPreferences.Editor.setBase(Base: String) {

        with(this) {

            putString(SharedPrefKey.Base.toString(), Base)
            apply()
        }
    }

    private fun SharedPreferences.Editor.setFilter(sortFilter: String) {

        val sort = when (sortFilter) {
            "Değer" -> "price"
            "Piyasa Değeri" -> "marketCap"
            "Değişim" -> "change"
            else -> "coinranking"

        }

        with(this) {

            putString(SharedPrefKey.SortFilter.toString(), sort)
            apply()
        }
    }

    private fun SharedPreferences.Editor.setCheckedBaseButtonID(ButtonID: Int) {

        with(this) {

            putInt(SharedPrefKey.CheckedBaseButtonID.toString(), ButtonID)
            apply()
        }
    }

    private fun SharedPreferences.Editor.setCheckedSortingButtonID(ButtonID: Int) {

        with(this) {

            putInt(SharedPrefKey.CheckedSortFilterButtonID.toString(), ButtonID)
            apply()
        }
    }

}


