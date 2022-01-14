package com.birthday.kotlin.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDDMMYYYY(): String {
    return SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(this)
}