package com.espoir.easyadv.config

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd
import com.espoir.easyadv.CallbackType
import com.espoir.easyadv.EasyAdv
import com.espoir.easyadv.FullScreenVideoAdvListener
import com.espoir.easyadv.interceptor.AdvInterceptor
import java.lang.ref.WeakReference

open class FullScreenVideoAdvConfig : BaseAdvConfig() {
    var codeId: String = ""
    internal var fullScreenVideoAdvListener: FullScreenVideoAdvListener? = null
    internal var lifecycleOwner: LifecycleOwner? = null
    internal var lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY

    fun setCodeId(codeId: String) = apply {
        this.codeId = codeId
    }

    fun setFullScreenVideoAdvListener(listener: FullScreenVideoAdvListener) = apply {
        this.fullScreenVideoAdvListener = listener
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

    fun showFullScreenVideoAdv() {
        EasyAdv.showFullScreenVideoAdv(this)
    }
}

fun FullScreenVideoAdvConfig.listener(listenerType: CallbackType, vararg params: Any?) {
    when (listenerType) {
        CallbackType.ERROR -> {
            fullScreenVideoAdvListener?.onError(params[0] as Int, params[1] as String)
            EasyAdv.globalConfig()?.fullScreenVideoAdvListener?.onError(params[0] as Int, params[1] as String)
        }

        CallbackType.AD_LOAD -> {
            fullScreenVideoAdvListener?.onFullScreenVideoAdLoad(params[0] as TTFullScreenVideoAd?)
            EasyAdv.globalConfig()?.fullScreenVideoAdvListener?.onFullScreenVideoAdLoad(params[0] as TTFullScreenVideoAd?)
        }

        CallbackType.AD_CACHE -> {
            fullScreenVideoAdvListener?.onFullScreenVideoAdCache(params[0] as TTFullScreenVideoAd?)
            EasyAdv.globalConfig()?.fullScreenVideoAdvListener?.onFullScreenVideoAdCache(params[0] as TTFullScreenVideoAd?)
        }

        CallbackType.AD_CLICK -> {
            fullScreenVideoAdvListener?.onAdClicked(params[0] as String, params[1] as TTFullScreenVideoAd?)
            EasyAdv.globalConfig()?.fullScreenVideoAdvListener?.onAdClicked(
                params[0] as String,
                params[1] as TTFullScreenVideoAd?
            )
        }

        CallbackType.AD_SHOW -> {
            fullScreenVideoAdvListener?.onAdShow(params[0] as String, params[1] as TTFullScreenVideoAd?)
            EasyAdv.globalConfig()?.fullScreenVideoAdvListener?.onAdShow(
                params[0] as String,
                params[1] as TTFullScreenVideoAd?
            )
        }

        CallbackType.AD_SKIP -> {
            fullScreenVideoAdvListener?.onAdSkip(params[0] as String, params[1] as TTFullScreenVideoAd?)
            EasyAdv.globalConfig()?.fullScreenVideoAdvListener?.onAdSkip(
                params[0] as String,
                params[1] as TTFullScreenVideoAd?
            )
        }

        else -> {}
    }
}