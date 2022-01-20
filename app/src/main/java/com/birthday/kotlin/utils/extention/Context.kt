package com.birthday.kotlin.utils.extention

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun Context.showDialogWithList(
    dialogTitle: Int,
    items: Array<String>,
    listeners: DialogInterface.OnClickListener
) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder.setTitle(resources.getString(dialogTitle))
    builder.setItems(items, listeners)
    val dialog = builder.create()
    dialog.show()
}