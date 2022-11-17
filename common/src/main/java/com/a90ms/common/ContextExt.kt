package com.a90ms.common

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Context?.isValidContext(): Boolean = when (this) {
    null -> false
    is Activity -> (isDestroyed || isFinishing).not()
    else -> true
}

fun Context.toast(msg: CharSequence) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
