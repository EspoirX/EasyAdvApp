package com.espoir.easyadv.config

import android.app.Activity
import com.espoir.easyadv.interceptor.AdvInterceptor
import java.lang.ref.WeakReference

open class BaseAdvConfig {
    var activity: WeakReference<Activity>? = null
    var width: Int = 0
    var height: Int = 0
    var adCount: Int = 1
    var userId: String? = null
}