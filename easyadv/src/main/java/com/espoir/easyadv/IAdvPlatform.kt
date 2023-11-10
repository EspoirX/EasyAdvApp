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
}

interface AdvSdkInitCallback {
    fun onInitSuccess()
    fun onInitFail(code: Int, msg: String?)
}

