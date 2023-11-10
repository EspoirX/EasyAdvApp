package com.espoir.easyadvapp.ad

import android.view.View
import androidx.core.view.isVisible
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.TTAdDislike
import com.bytedance.sdk.openadsdk.TTAdNative
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTNativeExpressAd
import com.espoir.easyadv.CallbackType
import com.espoir.easyadv.EasyAdv
import com.espoir.easyadv.IBannerAdvEngine
import com.espoir.easyadv.config.BannerAdvConfig
import com.espoir.easyadv.config.listener

class TTBannerAdvEngine : IBannerAdvEngine {
    override fun showBannerAdv(config: BannerAdvConfig) {
        if (config.activity == null) {
            config.listener(CallbackType.ERROR, EasyAdv.ERROR_ACT, "activity is null")
            return
        }
        if (config.codeId.isEmpty()) {
            config.listener(CallbackType.ERROR, EasyAdv.ERROR_PARAM, "codeId is null")
            return
        }
        val act = config.activity?.get() ?: return
        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(act)
        val adSlot = AdSlot.Builder()
            .setAdCount(config.adCount)
            .setCodeId(config.codeId)
            .setImageAcceptedSize(config.width, config.height) // 单位px
            .build()
        adNativeLoader.loadBannerExpressAd(adSlot, object : TTAdNative.NativeExpressAdListener {
            override fun onError(code: Int, msg: String?) {
                config.listener(CallbackType.ERROR, code, msg)
            }

            override fun onNativeExpressAdLoad(list: MutableList<TTNativeExpressAd>?) {
                config.listener(CallbackType.AD_LOAD, config.codeId, list?.getOrNull(0))
                list?.getOrNull(0)?.let {
                    showBannerAdImpl(config, it)
                }
            }
        })
    }

    private fun showBannerAdImpl(config: BannerAdvConfig, bannerAd: TTNativeExpressAd?) {
        bannerAd?.setExpressInteractionListener(object : TTNativeExpressAd.ExpressAdInteractionListener {
            override fun onAdClicked(view: View?, i: Int) {
                config.listener(CallbackType.AD_CLICK, config.codeId, bannerAd)
            }

            override fun onAdShow(view: View?, i: Int) {
                config.listener(CallbackType.AD_SHOW, config.codeId, bannerAd)
            }

            override fun onRenderFail(view: View?, s: String, i: Int) {
            }

            override fun onRenderSuccess(view: View?, v: Float, v1: Float) {
            }
        })
        bannerAd?.setDislikeCallback(config.activity?.get(), object : TTAdDislike.DislikeInteractionCallback {
            override fun onShow() {
            }

            override fun onSelected(i: Int, s: String, b: Boolean) {
            }

            override fun onCancel() {}
        })
        config.container?.let {
            it.isVisible = true
            it.removeAllViews()
            it.addView(bannerAd?.expressAdView)
        }
    }
}