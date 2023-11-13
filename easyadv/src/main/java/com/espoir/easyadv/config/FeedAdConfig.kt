package com.espoir.easyadv.config

import android.app.Activity
import android.view.ViewGroup
import com.bytedance.sdk.openadsdk.TTFeedAd
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo
import com.espoir.easyadv.CallbackType
import com.espoir.easyadv.EasyAdv
import com.espoir.easyadv.FeedAdvListener
import com.espoir.easyadv.interceptor.AdvInterceptor
import java.lang.ref.WeakReference

class FeedAdConfig : BaseAdvConfig() {
    var codeId: String = ""
    var container: ViewGroup? = null
    var feedAdvListener: FeedAdvListener? = null

    fun setCodeId(codeId: String) = apply {
        this.codeId = codeId
    }

    fun setContainer(container: ViewGroup?) = apply {
        this.container = container
    }

    fun setFeedAdvListener(listener: FeedAdvListener) = apply {
        this.feedAdvListener = listener
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

    fun showFeedAdv() {
        EasyAdv.showFeedAdv(this)
    }
}

fun FeedAdConfig.listener(listenerType: CallbackType, vararg params: Any?) {
    when (listenerType) {
        CallbackType.ERROR -> {
            feedAdvListener?.onError(params[0] as Int, params[1] as String)
            EasyAdv.globalConfig()?.bannerAdvListener?.onError(params[0] as Int, params[1] as String)
        }

        CallbackType.AD_LOAD -> {
            val list = params[1] as MutableList<TTFeedAd>?
            feedAdvListener?.onFeedAdLoad(params[0] as String, list)
            EasyAdv.globalConfig()?.feedAdvListener?.onFeedAdLoad(params[0] as String, list)
        }

        CallbackType.AD_CLICK -> {
            feedAdvListener?.onFeedAdClick(params[0] as String, params[1] as MediationAdEcpmInfo?)
            EasyAdv.globalConfig()?.feedAdvListener?.onFeedAdClick(
                params[0] as String,
                params[1] as MediationAdEcpmInfo?
            )
        }

        CallbackType.AD_SHOW -> {
            feedAdvListener?.onFeedAdShow(params[0] as String, params[1] as MediationAdEcpmInfo?)
            EasyAdv.globalConfig()?.feedAdvListener?.onFeedAdShow(
                params[0] as String,
                params[1] as MediationAdEcpmInfo?
            )
        }

        else -> {}
    }
}