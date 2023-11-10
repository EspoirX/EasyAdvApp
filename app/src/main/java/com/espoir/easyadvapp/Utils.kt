package com.espoir.easyadvapp

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager

fun Context.getScreenWidth(): Int {
    return resources.displayMetrics.widthPixels
}

fun Context.getScreenHeight(): Int {
    val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
    val dm = DisplayMetrics()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        display.getRealMetrics(dm)
    } else {
        display.getMetrics(dm)
    }
    return dm.heightPixels
}