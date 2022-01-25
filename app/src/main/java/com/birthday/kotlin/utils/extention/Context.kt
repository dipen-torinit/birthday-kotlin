package com.birthday.kotlin.utils.extention

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun Context.showDialogWithList(
    dialogTitle: Int,
    items: Array<String>,
    listeners: DialogInterface.OnClickListener
) {
    AlertDialog.Builder(this).apply {
        setTitle(resources.getString(dialogTitle))
        setItems(items, listeners)
    }.create().show()
}