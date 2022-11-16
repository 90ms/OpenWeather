package com.a90ms.common

import java.text.SimpleDateFormat
import java.util.*

const val DATE_PATTERN_yyyy_MM_dd = "yyyy-MM-dd"

fun commonSdf(pattern: String) = SimpleDateFormat(pattern, Locale.KOREA)

fun Long.convertTimestampToDate(): String = commonSdf(DATE_PATTERN_yyyy_MM_dd).format(this)
