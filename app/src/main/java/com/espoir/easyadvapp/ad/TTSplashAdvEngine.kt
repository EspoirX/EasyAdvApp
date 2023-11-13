package com.espoir.easyadvapp.ad

//class TTSplashAdvEngine : ISplashAdvEngine {
//
//    override fun showSplashAdv(config: SplashAdvConfig) {
//        if (config.activity == null) {
//            config.listener(CallbackType.ERROR, EasyAdv.ERROR_ACT, "activity is null")
//            return
//        }
//        if (config.codeId.isEmpty() || config.container == null) {
//            config.listener(CallbackType.ERROR, EasyAdv.ERROR_PARAM, "codeId or container is null")
//            return
//        }
//        val act = config.activity?.get() ?: return
//        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(act)
//        val adSlot = AdSlot.Builder()
//            .setCodeId(config.codeId)
//            .setImageAcceptedSize(config.width, config.height)
//            .build()
//        adNativeLoader.loadSplashAd(adSlot, object : TTAdNative.SplashAdListener {
//            override fun onError(code: Int, message: String?) {
//                config.listener(CallbackType.ERROR, code, message)
//            }
//
//            override fun onTimeout() {
//                config.listener(CallbackType.TIME_OUT)
//            }
//
//            override fun onSplashAdLoad(splashAd: TTSplashAd?) {
//                config.listener(CallbackType.AD_LOAD, splashAd)
//                showSplashAdvImpl(splashAd, config)
//            }
//        })
//    }
//
//    private fun showSplashAdvImpl(splashAd: TTSplashAd?, config: SplashAdvConfig) {
//        if (splashAd == null) {
//            config.listener(CallbackType.ERROR, EasyAdv.ERROR_AD_INFO, "splashAd is null")
//            return
//        }
//        splashAd.setSplashInteractionListener(object : TTSplashAd.AdInteractionListener {
//            override fun onAdClicked(view: View?, type: Int) {
//                config.listener(CallbackType.AD_CLICK, view, type)
//            }
//
//            override fun onAdShow(view: View?, type: Int) {
//                config.listener(CallbackType.AD_SHOW, config.codeId, view, type)
//            }
//
//            override fun onAdSkip() {
//                config.listener(CallbackType.AD_SKIP)
//            }
//
//            override fun onAdTimeOver() {
//                config.listener(CallbackType.TIME_OVER)
//            }
//        })
//        config.container?.let {
//            it.isVisible = true
//            it.removeAllViews()
//            it.addView(splashAd.splashView)
//        }
//    }
//}