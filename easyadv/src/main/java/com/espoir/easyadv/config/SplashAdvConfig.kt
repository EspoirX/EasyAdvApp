package com.espoir.easyadv.config

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.espoir.easyadv.config.BaseAdvConfig
import com.espoir.easyadv.EasyAdv
import com.espoir.easyadv.CallbackType
import com.espoir.easyadv.SplashAdvListener
import com.bytedance.sdk.openadsdk.TTSplashAd
import java.lang.ref.WeakReference

class SplashAdvConfig : BaseAdvConfig() {
    internal var codeId: String = ""
    internal var container: ViewGroup? = null
    internal var splashAdvListener: SplashAdvListener? = null

    fun setCodeId(codeId: String) = apply {
        this.codeId = codeId
    }

    fun setContainer(container: ViewGroup?) = apply {
        this.container = container
    }

    fun setSplashAdvListener(listener: SplashAdvListener) = apply {
        this.splashAdvListener = listener
    }

    fun setActivity(activity: Activity?) = apply {
        this.activity = WeakReference(activity)
    }

    fun setWidth(width: Int) = apply {
        this.width = width
    }

    fun setHeight(height: Int) = apply {
        this.height = height
    }

    fun setUserId(userId: String) = apply {
        this.userId = userId
    }

    fun setAdCount(count: Int) = apply {
        this.adCount = count
    }

    fun showSplashAdv() {
        EasyAdv.showSplashAdv(this)
    }
}

fun SplashAdvConfig.listener(listenerType: CallbackType, vararg params: Any?) {
    when (listenerType) {
        CallbackType.ERROR -> {
            splashAdvListener?.onError(params[0] as Int, params[1] as String)
            EasyAdv.globalConfig()?.splashAdvListener?.onError(params[0] as Int, params[1] as String)
        }

        CallbackType.TIME_OUT -> {
            splashAdvListener?.onTimeout()
            EasyAdv.globalConfig()?.splashAdvListener?.onTimeout()
        }

        CallbackType.AD_LOAD -> {
            splashAdvListener?.onSplashAdLoad(params[0] as TTSplashAd?)
            EasyAdv.globalConfig()?.splashAdvListener?.onSplashAdLoad(params[0] as TTSplashAd?)
        }

        CallbackType.AD_CLICK -> {
            splashAdvListener?.onAdClicked(params[0] as View?, params[1] as Int)
            EasyAdv.globalConfig()?.splashAdvListener?.onAdClicked(params[0] as View?, params[1] as Int)
        }

        CallbackType.AD_SHOW -> {
            splashAdvListener?.onAdShow(params[0] as String, params[1] as View?, params[2] as Int)
            EasyAdv.globalConfig()?.splashAdvListener?.onAdShow(
                params[0] as String,
                params[1] as View?,
                params[2] as Int
            )
        }

        CallbackType.AD_SKIP -> {
            splashAdvListener?.onAdSkip()
            EasyAdv.globalConfig()?.splashAdvListener?.onAdSkip()
        }

        CallbackType.TIME_OVER -> {
            splashAdvListener?.onAdTimeOver()
            EasyAdv.globalConfig()?.splashAdvListener?.onAdTimeOver()
        }

        else -> {}
    }
}