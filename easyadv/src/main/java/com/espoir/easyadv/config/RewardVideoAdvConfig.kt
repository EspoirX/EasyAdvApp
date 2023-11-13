package com.espoir.easyadv.config

import android.app.Activity
import android.os.Bundle
import com.bytedance.sdk.openadsdk.TTAdConstant
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo
import com.espoir.easyadv.CallbackType
import com.espoir.easyadv.EasyAdv
import com.espoir.easyadv.RewardVideoAdvListener
import com.espoir.easyadv.interceptor.AdvInterceptor
import java.lang.ref.WeakReference

class RewardVideoAdvConfig : BaseAdvConfig() {
    var codeId: String = ""
    var orientation: Int = TTAdConstant.VERTICAL
    internal var interceptors = mutableListOf<AdvInterceptor>()
    var extraMap = hashMapOf<String, String>()
    var rewardVideoAdvListener: RewardVideoAdvListener? = null

    fun setCodeId(codeId: String) = apply {
        this.codeId = codeId
    }

    fun setOrientation(orientation: Int) = apply {
        this.orientation = orientation
    }

    fun setRewardVideoAdvListener(listener: RewardVideoAdvListener) = apply {
        this.rewardVideoAdvListener = listener
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

    fun addInterceptor(interceptor: AdvInterceptor) = apply {
        val noSame = interceptors.none { it.getTag() == interceptor.getTag() }
        if (noSame) { //如果没有相同的才添加
            interceptors += interceptor
        }
    }

    fun showRewardVideoAdv() {
        EasyAdv.showRewardVideoAdv(this)
    }
}

fun RewardVideoAdvConfig.listener(listenerType: CallbackType, vararg params: Any?) {
    when (listenerType) {
        CallbackType.ERROR -> {
            rewardVideoAdvListener?.onError(params[0] as Int, params[1] as String)
            EasyAdv.globalConfig()?.rewardVideoAdvListener?.onError(params[0] as Int, params[1] as String)
        }

        CallbackType.AD_LOAD -> {
            rewardVideoAdvListener?.onRewardVideoAdLoad(params[0] as String, params[1] as MediationAdEcpmInfo?)
            EasyAdv.globalConfig()?.rewardVideoAdvListener?.onRewardVideoAdLoad(
                params[0] as String,
                params[1] as MediationAdEcpmInfo?
            )
        }

        CallbackType.AD_CLICK -> {
            rewardVideoAdvListener?.onAdVideoBarClick(params[0] as String, params[1] as MediationAdEcpmInfo?)
            EasyAdv.globalConfig()?.rewardVideoAdvListener?.onAdVideoBarClick(
                params[0] as String,
                params[1] as MediationAdEcpmInfo?
            )
        }

        CallbackType.AD_SHOW -> {
            rewardVideoAdvListener?.onRewardVideoAdShow(params[0] as String, params[1] as MediationAdEcpmInfo?)
            EasyAdv.globalConfig()?.rewardVideoAdvListener?.onRewardVideoAdShow(
                params[0] as String,
                params[1] as MediationAdEcpmInfo?
            )
        }

        CallbackType.AD_CLOSE -> {
            rewardVideoAdvListener?.onRewardVideoAdClose(
                params[0] as String,
                params[1] as MediationAdEcpmInfo?,
                params[2] as Long,
                params[3] as Boolean
            )
            EasyAdv.globalConfig()?.rewardVideoAdvListener?.onRewardVideoAdClose(
                params[0] as String,
                params[1] as MediationAdEcpmInfo?,
                params[2] as Long,
                params[3] as Boolean
            )
        }

        CallbackType.VIDEO_COMPLETE -> {
            rewardVideoAdvListener?.onVideoComplete(
                params[0] as String,
                params[1] as MediationAdEcpmInfo?,
                params[2] as Long,
                params[3] as Boolean
            )
            EasyAdv.globalConfig()?.rewardVideoAdvListener?.onVideoComplete(
                params[0] as String,
                params[1] as MediationAdEcpmInfo?,
                params[2] as Long,
                params[3] as Boolean
            )
        }

        CallbackType.REWARD_VERIFY -> {
            rewardVideoAdvListener?.onRewardVerify(
                params[0] as Boolean,
                params[1] as Int,
                params[2] as String?,
                params[3] as Int,
                params[4] as String?
            )
            EasyAdv.globalConfig()?.rewardVideoAdvListener?.onRewardVerify(
                params[0] as Boolean,
                params[1] as Int,
                params[2] as String?,
                params[3] as Int,
                params[4] as String?
            )
        }

        CallbackType.REWARD_ARRIVED -> {
            rewardVideoAdvListener?.onRewardArrived(
                params[0] as Boolean,
                params[1] as Int,
                params[2] as Bundle?
            )
            EasyAdv.globalConfig()?.rewardVideoAdvListener?.onRewardArrived(
                params[0] as Boolean,
                params[1] as Int,
                params[2] as Bundle?
            )
        }

        CallbackType.AD_SKIP -> {
            rewardVideoAdvListener?.onSkippedVideo(params[0] as String, params[1] as MediationAdEcpmInfo?)
            EasyAdv.globalConfig()?.rewardVideoAdvListener?.onSkippedVideo(
                params[0] as String,
                params[1] as MediationAdEcpmInfo?
            )
        }

        else -> {}
    }
}