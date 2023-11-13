package com.espoir.easyadvapp.ad

//class TTFullScreenVideoAdvEngine : IFullScreenVideoAdvEngine {
//    override fun showFullScreenVideoAdv(config: FullScreenVideoAdvConfig) {
//        if (config.activity == null) {
//            config.listener(CallbackType.ERROR, EasyAdv.ERROR_ACT, "activity is null")
//            return
//        }
//        if (config.codeId.isEmpty()) {
//            config.listener(CallbackType.ERROR, EasyAdv.ERROR_PARAM, "codeId is null")
//            return
//        }
//        val act = config.activity?.get() ?: return
//        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(act)
//        val adslot = AdSlot.Builder()
//            .setCodeId(config.codeId)
//            .setAdCount(config.adCount)//请求的广告数
//            .setUserID(config.userId)
//            .setMediationAdSlot(
//                MediationAdSlot.Builder().setMuted(true).setVolume(0.7f)
//                    .build()
//            )
//            .setOrientation(TTAdConstant.ORIENTATION_VERTICAL) //设置横竖屏方向
//            .build()
//        adNativeLoader.loadFullScreenVideoAd(adslot, object : TTAdNative.FullScreenVideoAdListener {
//            override fun onError(code: Int, message: String) {
//                config.listener(CallbackType.ERROR, code, message)
//            }
//
//            override fun onFullScreenVideoAdLoad(ad: TTFullScreenVideoAd) {
//                config.listener(CallbackType.AD_LOAD, ad)
//                showFullScreenVideoAdImpl(config, ad)
//            }
//
//            override fun onFullScreenVideoCached() {
//            }
//
//            override fun onFullScreenVideoCached(ad: TTFullScreenVideoAd) {
//                config.listener(CallbackType.AD_CACHE, ad)
//                showFullScreenVideoAdImpl(config, ad)
//            }
//        })
//    }
//
//    private var fullScreenStartTime: Long = 0
//
//    private fun showFullScreenVideoAdImpl(config: FullScreenVideoAdvConfig, ad: TTFullScreenVideoAd?) {
//        ad?.setFullScreenVideoAdInteractionListener(object :
//            TTFullScreenVideoAd.FullScreenVideoAdInteractionListener {
//            override fun onAdShow() {
//                fullScreenStartTime = System.currentTimeMillis()
//                config.listener(CallbackType.AD_SHOW, config.codeId, ad)
//            }
//
//            override fun onAdVideoBarClick() {
//                config.listener(CallbackType.AD_CLICK, config.codeId, ad)
//            }
//
//            override fun onAdClose() {
//                config.listener(CallbackType.AD_CLOSE)
//            }
//
//            override fun onVideoComplete() {
//                if (fullScreenStartTime > 0) {
//                    val time = System.currentTimeMillis() - fullScreenStartTime
//                    config.listener(CallbackType.VIDEO_COMPLETE, config.codeId, time, true, ad)
//                    fullScreenStartTime = 0
//                }
//            }
//
//            override fun onSkippedVideo() {
//                config.listener(CallbackType.AD_SKIP, config.codeId, ad)
//                if (fullScreenStartTime > 0) {
//                    val time = System.currentTimeMillis() - fullScreenStartTime
//                    config.listener(CallbackType.VIDEO_COMPLETE, config.codeId, time, false, ad)
//                    fullScreenStartTime = 0
//                }
//            }
//        })
//        ad?.showFullScreenVideoAd(config.activity?.get())
//    }
//}