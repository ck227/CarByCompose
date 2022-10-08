package com.ck.car2.utils

import android.content.Context
import android.widget.Toast

class Utils {

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}