package com.espoir.easyadvapp.ad

//class TTFeedAdvStrategy : IFeedAdvEngine {
//    override fun showFeedAdv(config: FeedAdConfig) {
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
//        val adSlot = AdSlot.Builder()
//            .setCodeId(config.codeId)
//            .setImageAcceptedSize(config.width, config.height) // 单位px
//            .setAdCount(config.adCount)
//            .setUserID(config.userId)
//            .build()
//        adNativeLoader.loadFeedAd(adSlot, object : TTAdNative.FeedAdListener {
//            override fun onError(code: Int, msg: String?) {
//                config.listener(CallbackType.ERROR, code, msg)
//            }
//
//            override fun onFeedAdLoad(list: MutableList<TTFeedAd>?) {
//                config.listener(CallbackType.AD_LOAD, config.codeId, list)
//                list?.getOrNull(0)?.let {
//                    showFeedAdImpl(config, act, it)
//                }
//            }
//        })
//    }
//
//    private fun showFeedAdImpl(config: FeedAdConfig, act: Activity, feedAd: TTFeedAd) {
//        feedAd.setDislikeCallback(act, object : TTAdDislike.DislikeInteractionCallback {
//            override fun onShow() {
//            }
//
//            override fun onSelected(p0: Int, p1: String?, p2: Boolean) {
//                config.container?.removeAllViews()
//                config.container?.visibility = View.GONE
//            }
//
//            override fun onCancel() {
//            }
//        })
//        feedAd.mediationManager?.let {
//            if (it.isExpress) {
//                feedAd.setExpressRenderListener(object : MediationExpressRenderListener {
//                    override fun onRenderSuccess(p0: View?, p1: Float, p2: Float, p3: Boolean) {
//                        feedAd.adView?.let { view ->
//                            if (view.parent != null) {
//                                (view.parent as ViewGroup).removeView(view)
//                            }
//                            config.container?.visibility = View.VISIBLE
//                            config.container?.removeAllViews()
//                            config.container?.addView(view)
//                        }
//                    }
//
//                    override fun onRenderFail(p0: View?, p1: String?, p2: Int) {
//                    }
//
//                    override fun onAdClick() {
//                        config.listener(CallbackType.AD_CLICK, config.codeId, feedAd.mediationManager.showEcpm)
//                    }
//
//                    override fun onAdShow() {
//                        config.listener(CallbackType.AD_SHOW, config.codeId, feedAd.mediationManager.showEcpm)
//                    }
//                })
//                feedAd.render()
//            }
//        }
//    }
//}