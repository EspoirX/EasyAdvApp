package com.espoir.easyadv

import android.app.Activity

interface IHotSplashAdvStrategy {
    fun getCodeId(): String

    fun showRequirement(activity: Activity): Boolean

    fun showHotSplashAdv(activity: Activity, codeId: String)
}