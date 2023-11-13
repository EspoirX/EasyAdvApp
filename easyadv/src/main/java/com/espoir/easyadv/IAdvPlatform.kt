package com.espoir.easyadv

import android.content.Context

interface ISdkPlatform {
    fun initAdvSdk(
        builder: AdvSDKBuilder,
        callback: AdvSdkInitCallback
    )
}

class AdvSDKBuilder {
    var context: Context? = null
    var appId: String? = null
    var appName: String? = null
    var userId: String? = null
    var debug: Boolean = false
    var timeOutMillis: Long = 0  //sdk超时时间，0就是没
}

interface AdvSdkInitCallback {
    fun onInitSuccess()
    fun onInitFail(code: Int, msg: String?)
}

