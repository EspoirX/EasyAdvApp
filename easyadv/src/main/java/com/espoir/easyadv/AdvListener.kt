package com.espoir.easyadv

import android.os.Bundle
import android.view.View
import com.bytedance.sdk.openadsdk.TTFeedAd
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd
import com.bytedance.sdk.openadsdk.TTNativeExpressAd
import com.bytedance.sdk.openadsdk.TTSplashAd
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo
import com.espoir.easyadv.config.BannerAdvConfig
import com.espoir.easyadv.config.FeedAdConfig
import com.espoir.easyadv.config.FullScreenVideoAdvConfig
import com.espoir.easyadv.config.RewardVideoAdvConfig
import com.espoir.easyadv.config.SplashAdvConfig

interface SplashAdvListener {
    fun onError(code: Int, message: String?)
    fun onTimeout()
    fun onSplashAdLoad(splashAd: TTSplashAd?)
    fun onAdClicked(view: View?, type: Int)
    fun onAdShow(codeId: String, view: View?, type: Int)
    fun onAdSkip()
    fun onAdTimeOver()
}

interface FullScreenVideoAdvListener {
    fun onError(code: Int, message: String?)
    fun onFullScreenVideoAdLoad(ad: TTFullScreenVideoAd?)
    fun onFullScreenVideoAdCache(ad: TTFullScreenVideoAd?)
    fun onAdClicked(codeId: String, ad: TTFullScreenVideoAd?)
    fun onAdShow(codeId: String, ad: TTFullScreenVideoAd?)
    fun onAdSkip(codeId: String, ad: TTFullScreenVideoAd?)
    fun onAdClose()
    fun onVideoComplete(codeId: String, duration: Long, isPlayOver: Boolean, ad: TTFullScreenVideoAd?)
}

interface BannerAdvListener {
    fun onError(code: Int, message: String?)
    fun onBannerAdLoad(codeId: String, bannerAd: TTNativeExpressAd?)
    fun onAdClicked(codeId: String, bannerAd: TTNativeExpressAd?)
    fun onAdShow(codeId: String, bannerAd: TTNativeExpressAd?)
}

interface FeedAdvListener {
    fun onError(code: Int, message: String?)

    fun onFeedAdLoad(codeId: String, list: MutableList<TTFeedAd>?)

    fun onFeedAdClick(codeId: String, ecpm: MediationAdEcpmInfo?)
    fun onFeedAdShow(codeId: String, ecpm: MediationAdEcpmInfo?)
}

interface RewardVideoAdvListener {
    fun onError(code: Int, message: String?)

    fun onRewardVideoAdLoad(codeId: String, ecpm: MediationAdEcpmInfo?)

    fun onRewardVideoAdShow(codeId: String, ecpm: MediationAdEcpmInfo?)

    fun onAdVideoBarClick(codeId: String, ecpm: MediationAdEcpmInfo?)
    fun onRewardVideoAdClose(codeId: String, ecpm: MediationAdEcpmInfo?, duration: Long, isPlayOver: Boolean)

    fun onVideoComplete(codeId: String, ecpm: MediationAdEcpmInfo?, duration: Long, isPlayOver: Boolean)

    fun onRewardVerify(rewardVerify: Boolean, rewardAmount: Int, rewardName: String?, errorCode: Int, errorMsg: String?)

    fun onRewardArrived(isRewardValid: Boolean, rewardType: Int, extraInfo: Bundle?)

    fun onSkippedVideo(codeId: String, ecpm: MediationAdEcpmInfo?)
}

inline fun SplashAdvConfig.setSplashAdvListener(
    crossinline onError: (code: Int, message: String?) -> Unit = { _, _ -> },
    crossinline onTimeout: () -> Unit = {},
    crossinline onSplashAdLoad: (splashAd: TTSplashAd?) -> Unit = {},
    crossinline onAdClicked: (view: View?, type: Int) -> Unit = { _, _ -> },
    crossinline onAdShow: (codeId: String, view: View?, type: Int) -> Unit = { _, _, _ -> },
    crossinline onAdSkip: () -> Unit = {},
    crossinline onAdTimeOver: () -> Unit = {}
): SplashAdvConfig {
    val listener = object : SplashAdvListener {
        override fun onError(code: Int, message: String?) {
            onError(code, message)
        }

        override fun onTimeout() {
            onTimeout()
        }

        override fun onSplashAdLoad(splashAd: TTSplashAd?) {
            onSplashAdLoad(splashAd)
        }

        override fun onAdClicked(view: View?, type: Int) {
            onAdClicked(view, type)
        }

        override fun onAdShow(codeId: String, view: View?, type: Int) {
            onAdShow(codeId, view, type)
        }

        override fun onAdSkip() {
            onAdSkip()
        }

        override fun onAdTimeOver() {
            onAdTimeOver()
        }
    }
    setSplashAdvListener(listener)
    return this
}

inline fun FullScreenVideoAdvConfig.setFullScreenVideoAdvListener(
    crossinline onError: (code: Int, message: String?) -> Unit = { _, _ -> },
    crossinline onFullScreenVideoAdLoad: (ad: TTFullScreenVideoAd?) -> Unit = {},
    crossinline onFullScreenVideoAdCache: (ad: TTFullScreenVideoAd?) -> Unit = {},
    crossinline onAdClicked: (codeId: String, ad: TTFullScreenVideoAd?) -> Unit = { _, _ -> },
    crossinline onAdShow: (codeId: String, ad: TTFullScreenVideoAd?) -> Unit = { _, _ -> },
    crossinline onAdSkip: (codeId: String, ad: TTFullScreenVideoAd?) -> Unit = { _, _ -> },
    crossinline onAdClose: () -> Unit = {},
    crossinline onVideoComplete: (codeId: String, duration: Long, isPlayOver: Boolean, ad: TTFullScreenVideoAd?) -> Unit = { _, _, _, _ -> }
): FullScreenVideoAdvConfig {
    val listener = object : FullScreenVideoAdvListener {
        override fun onError(code: Int, message: String?) {
            onError(code, message)
        }

        override fun onFullScreenVideoAdLoad(ad: TTFullScreenVideoAd?) {
            onFullScreenVideoAdLoad(ad)
        }

        override fun onFullScreenVideoAdCache(ad: TTFullScreenVideoAd?) {
            onFullScreenVideoAdCache(ad)
        }

        override fun onAdClicked(codeId: String, ad: TTFullScreenVideoAd?) {
            onAdClicked(codeId, ad)
        }

        override fun onAdShow(codeId: String, ad: TTFullScreenVideoAd?) {
            onAdShow(codeId, ad)
        }

        override fun onAdSkip(codeId: String, ad: TTFullScreenVideoAd?) {
            onAdSkip(codeId, ad)
        }

        override fun onAdClose() {
            onAdClose()
        }

        override fun onVideoComplete(codeId: String, duration: Long, isPlayOver: Boolean, ad: TTFullScreenVideoAd?) {
            onVideoComplete(codeId, duration, isPlayOver, ad)
        }
    }
    setFullScreenVideoAdvListener(listener)
    return this
}

inline fun BannerAdvConfig.setBannerAdvListener(
    crossinline onError: (code: Int, message: String?) -> Unit = { _, _ -> },
    crossinline onBannerAdLoad: (codeId: String, bannerAd: TTNativeExpressAd?) -> Unit = { _, _ -> },
    crossinline onAdClicked: (codeId: String, bannerAd: TTNativeExpressAd?) -> Unit = { _, _ -> },
    crossinline onAdShow: (codeId: String, bannerAd: TTNativeExpressAd?) -> Unit = { _, _ -> },
): BannerAdvConfig {
    val listener = object : BannerAdvListener {
        override fun onError(code: Int, message: String?) {
            onError(code, message)
        }

        override fun onBannerAdLoad(codeId: String, bannerAd: TTNativeExpressAd?) {
            onBannerAdLoad(codeId, bannerAd)
        }

        override fun onAdClicked(codeId: String, bannerAd: TTNativeExpressAd?) {
            onAdClicked(codeId, bannerAd)
        }

        override fun onAdShow(codeId: String, bannerAd: TTNativeExpressAd?) {
            onAdShow(codeId, bannerAd)
        }
    }
    setBannerAdvListener(listener)
    return this
}

inline fun FeedAdConfig.setFeedAdvListener(
    crossinline onError: (code: Int, message: String?) -> Unit = { _, _ -> },
    crossinline onFeedAdLoad: (codeId: String, list: MutableList<TTFeedAd>?) -> Unit = { _, _ -> },
    crossinline onFeedAdClick: (codeId: String, ecpm: MediationAdEcpmInfo?) -> Unit = { _, _ -> },
    crossinline onFeedAdShow: (codeId: String, ecpm: MediationAdEcpmInfo?) -> Unit = { _, _ -> }
): FeedAdConfig {
    val listener = object : FeedAdvListener {
        override fun onError(code: Int, message: String?) {
            onError(code, message)
        }

        override fun onFeedAdLoad(codeId: String, list: MutableList<TTFeedAd>?) {
            onFeedAdLoad(codeId, list)
        }

        override fun onFeedAdClick(codeId: String, ecpm: MediationAdEcpmInfo?) {
            onFeedAdClick(codeId, ecpm)
        }

        override fun onFeedAdShow(codeId: String, ecpm: MediationAdEcpmInfo?) {
            onFeedAdShow(codeId, ecpm)
        }
    }
    setFeedAdvListener(listener)
    return this
}


inline fun RewardVideoAdvConfig.setRewardVideoAdvListener(
    crossinline onError: (code: Int, message: String?) -> Unit = { _, _ -> },
    crossinline onRewardVideoAdLoad: (codeId: String, ecpm: MediationAdEcpmInfo?) -> Unit = { _, _ -> },
    crossinline onRewardVideoAdShow: (codeId: String, ecpm: MediationAdEcpmInfo?) -> Unit = { _, _ -> },
    crossinline onAdVideoBarClick: (codeId: String, ecpm: MediationAdEcpmInfo?) -> Unit = { _, _ -> },
    crossinline onRewardVideoAdClose: (
        codeId: String,
        ecpm: MediationAdEcpmInfo?,
        duration: Long,
        isPlayOver: Boolean
    ) -> Unit = { _, _, _, _ -> },
    crossinline onVideoComplete: (codeId: String, ecpm: MediationAdEcpmInfo?, duration: Long, isPlayOver: Boolean) -> Unit = { _, _, _, _ -> },
    crossinline onRewardVerify: (
        rewardVerify: Boolean,
        rewardAmount: Int,
        rewardName: String?,
        errorCode: Int,
        errorMsg: String?
    ) -> Unit = { _, _, _, _, _ -> },
    crossinline onRewardArrived: (isRewardValid: Boolean, rewardType: Int, extraInfo: Bundle?) -> Unit = { _, _, _ -> },
    crossinline onSkippedVideo: (codeId: String, ecpm: MediationAdEcpmInfo?) -> Unit = { _, _ -> },
): RewardVideoAdvConfig {
    val listener = object : RewardVideoAdvListener {
        override fun onError(code: Int, message: String?) {
            onError(code, message)
        }

        override fun onRewardVideoAdLoad(codeId: String, ecpm: MediationAdEcpmInfo?) {
            onRewardVideoAdLoad(codeId, ecpm)
        }

        override fun onRewardVideoAdShow(codeId: String, ecpm: MediationAdEcpmInfo?) {
            onRewardVideoAdShow(codeId, ecpm)
        }

        override fun onAdVideoBarClick(codeId: String, ecpm: MediationAdEcpmInfo?) {
            onAdVideoBarClick(codeId, ecpm)
        }

        override fun onRewardVideoAdClose(
            codeId: String,
            ecpm: MediationAdEcpmInfo?,
            duration: Long,
            isPlayOver: Boolean
        ) {
            onRewardVideoAdClose(codeId, ecpm, duration, isPlayOver)
        }

        override fun onVideoComplete(codeId: String, ecpm: MediationAdEcpmInfo?, duration: Long, isPlayOver: Boolean) {
            onVideoComplete(codeId, ecpm, duration, isPlayOver)
        }

        override fun onRewardVerify(
            rewardVerify: Boolean,
            rewardAmount: Int,
            rewardName: String?,
            errorCode: Int,
            errorMsg: String?
        ) {
            onRewardVerify(rewardVerify, rewardAmount, rewardName, errorCode, errorMsg)
        }

        override fun onRewardArrived(isRewardValid: Boolean, rewardType: Int, extraInfo: Bundle?) {
            onRewardArrived(isRewardValid, rewardType, extraInfo)
        }

        override fun onSkippedVideo(codeId: String, ecpm: MediationAdEcpmInfo?) {
            onSkippedVideo(codeId, ecpm)
        }
    }
    setRewardVideoAdvListener(listener)
    return this
}