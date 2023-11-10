package com.espoir.easyadv.config

import android.app.Activity
import java.lang.ref.WeakReference

open class BaseAdvConfig {
    internal var activity: WeakReference<Activity>? = null
    internal var width: Int = 0
    internal var height: Int = 0
    internal var adCount: Int = 1
    internal var userId: String? = null
}