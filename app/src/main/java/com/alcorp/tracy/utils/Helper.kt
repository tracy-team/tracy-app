package com.alcorp.tracy.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.alcorp.tracy.R

fun setDatePickerNormal(year: Int, month: Int, day: Int): String? {
    val bln = if (month < 10) {
        "0$month"
    } else {
        month.toString()
    }
    val hri = if (day < 10) {
        "0$day"
    } else {
        day.toString()
    }
    return "$hri-$bln-$year"
}

fun dateToNormal(date: String): String? {
    return try {
        val b1 = date.substring(4)
        val b2 = b1.substring(2)
        val m = b1.substring(0, 2)
        val d = b2.substring(0, 2)
        val y = date.substring(0, 4)
        "$d/$m/$y"
    } catch (e: Exception) {
        "ini tanggal"
    }
}

fun convertDate(date: String): String {
    val a = date.split("/").toTypedArray()
    return a[2] + a[1] + a[0]
}

class LoadingDialog(private val context: Context) {
    private lateinit var dialog: Dialog

    fun showDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.loading_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.create()
        dialog.show()
    }

    fun hideDialog() {
        dialog.dismiss()
    }
}