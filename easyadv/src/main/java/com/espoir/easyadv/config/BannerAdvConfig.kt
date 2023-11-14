package com.espoir.easyadv.config

import android.app.Activity
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.bytedance.sdk.openadsdk.TTNativeExpressAd
import com.espoir.easyadv.BannerAdvListener
import com.espoir.easyadv.CallbackType
import com.espoir.easyadv.EasyAdv
import com.espoir.easyadv.interceptor.AdvInterceptor
import java.lang.ref.WeakReference

open class BannerAdvConfig : BaseAdvConfig() {
    var codeId: String = ""
    var container: ViewGroup? = null
    internal var bannerAdvListener: BannerAdvListener? = null
    internal var lifecycleOwner: LifecycleOwner? = null
    internal var lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY

    fun setCodeId(codeId: String) = apply {
        this.codeId = codeId
    }

    fun setContainer(container: ViewGroup?) = apply {
        this.container = container
    }

    fun setBannerAdvListener(listener: BannerAdvListener) = apply {
        this.bannerAdvListener = listener
    }

    fun setActivity(activity: Activity?) = apply {
        this.activity = WeakReference(activity)
    }

    fun lifecycle(lifecycleOwner: LifecycleOwner, lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY) = apply {
        this.lifecycleOwner = lifecycleOwner
        this.lifeEvent = lifeEvent
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

    fun showBannerAdv() {
        EasyAdv.showBannerAdv(this)
    }
}

fun BannerAdvConfig.listener(listenerType: CallbackType, vararg params: Any?) {
    when (listenerType) {
        CallbackType.ERROR -> {
            bannerAdvListener?.onError(params[0] as Int, params[1] as String)
            EasyAdv.globalConfig()?.bannerAdvListener?.onError(params[0] as Int, params[1] as String)
        }

        CallbackType.AD_LOAD -> {
            bannerAdvListener?.onBannerAdLoad(params[0] as String, params[1] as TTNativeExpressAd?)
            EasyAdv.globalConfig()?.bannerAdvListener?.onBannerAdLoad(
                params[0] as String,
                params[1] as TTNativeExpressAd?
            )
        }

        CallbackType.AD_CLICK -> {
            bannerAdvListener?.onAdClicked(params[0] as String, params[1] as TTNativeExpressAd?)
            EasyAdv.globalConfig()?.bannerAdvListener?.onAdClicked(
                params[0] as String,
                params[1] as TTNativeExpressAd?
            )
        }

        CallbackType.AD_SHOW -> {
            bannerAdvListener?.onAdShow(params[0] as String, params[1] as TTNativeExpressAd?)
            EasyAdv.globalConfig()?.bannerAdvListener?.onAdShow(
                params[0] as String,
                params[1] as TTNativeExpressAd?
            )
        }

        else -> {}
    }
}