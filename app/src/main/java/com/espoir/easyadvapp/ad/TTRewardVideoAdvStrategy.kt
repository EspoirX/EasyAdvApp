package com.espoir.easyadvapp.ad

//class TTRewardVideoAdvStrategy : IRewardVideoAdvEngine {
//
//    private var playtime: Long = 0L
//
//    override fun showRewardVideoAdv(config: RewardVideoAdvConfig) {
//        if (config.activity == null) {
//            config.listener(CallbackType.ERROR, EasyAdv.ERROR_ACT, "activity is null")
//            return
//        }
//        if (config.codeId.isEmpty()) {
//            config.listener(CallbackType.ERROR, EasyAdv.ERROR_PARAM, "codeId or container is null")
//            return
//        }
//        val act = config.activity?.get() ?: return
//        val adNativeLoader = TTAdSdk.getAdManager().createAdNative(act)
//        val adslot = AdSlot.Builder()
//            .setCodeId(config.codeId)
//            .setOrientation(config.orientation)
//            .setAdCount(config.adCount)
//            .setUserID(config.userId)
//            .setMediationAdSlot(
//                MediationAdSlot.Builder()
//                    .setMuted(true).setVolume(0.7f).apply {
//                        config.extraMap.forEach {
//                            setExtraObject(it.key, it.value)
//                        }
//                    }
//                    .build()
//            )
//            .build()
//        adNativeLoader.loadRewardVideoAd(adslot, object : TTAdNative.RewardVideoAdListener {
//            override fun onError(code: Int, msg: String) {
//                config.listener(CallbackType.ERROR, code, msg)
//            }
//
//            override fun onRewardVideoAdLoad(ttRewardVideoAd: TTRewardVideoAd) {
//                config.listener(CallbackType.AD_LOAD, config.codeId, ttRewardVideoAd.mediationManager?.showEcpm)
//            }
//
//            override fun onRewardVideoCached() {
//            }
//
//            override fun onRewardVideoCached(ttRewardVideoAd: TTRewardVideoAd) {
//                showRewardVideoAdImpl(config, act, ttRewardVideoAd)
//            }
//        })
//    }
//
//    private fun showRewardVideoAdImpl(config: RewardVideoAdvConfig, act: Activity, rewardAd: TTRewardVideoAd) {
//        rewardAd.setRewardAdInteractionListener(object : TTRewardVideoAd.RewardAdInteractionListener {
//            override fun onAdShow() {
//                playtime = System.currentTimeMillis()
//                config.listener(CallbackType.AD_SHOW, config.codeId, rewardAd.mediationManager.showEcpm)
//            }
//
//            /**
//             * 注意Admob的激励视频不会回调该方法
//             */
//            override fun onAdVideoBarClick() {
//                config.listener(CallbackType.AD_CLICK, config.codeId, rewardAd.mediationManager.showEcpm)
//            }
//
//            /**
//             * 广告关闭的回调
//             */
//            override fun onAdClose() {
//                if (playtime > 0) {
//                    val time = (System.currentTimeMillis() - playtime).toInt()
//                    config.listener(
//                        CallbackType.AD_CLOSE,
//                        config.codeId,
//                        rewardAd.mediationManager.showEcpm,
//                        time,
//                        false
//                    )
//                    playtime = 0
//                }
//            }
//
//            /**
//             * 视频播放完毕的回调 Admob广告不存在该回调
//             */
//            override fun onVideoComplete() {
//                if (playtime > 0) {
//                    val time = (System.currentTimeMillis() - playtime).toInt()
//                    config.listener(
//                        CallbackType.VIDEO_COMPLETE,
//                        config.codeId,
//                        rewardAd.mediationManager.showEcpm,
//                        time,
//                        true
//                    )
//                    playtime = 0
//                }
//            }
//
//            /**
//             * 视频播放失败的回调 - Mintegral GDT Admob广告不存在该回调
//             */
//            override fun onVideoError() {
//                config.listener(CallbackType.ERROR, -3870, "onVideoError")
//            }
//
//            /**
//             * 激励视频播放完毕，验证是否有效发放奖励的回调
//             */
//            override fun onRewardVerify(
//                rewardVerify: Boolean,
//                rewardAmount: Int,
//                rewardName: String?,
//                errorCode: Int,
//                errorMsg: String?
//            ) {
//                config.listener(
//                    CallbackType.REWARD_VERIFY,
//                    rewardVerify,
//                    rewardAmount,
//                    rewardName,
//                    errorCode,
//                    errorMsg
//                )
//            }
//
//            override fun onRewardArrived(
//                isRewardValid: Boolean,
//                rewardType: Int,
//                extraInfo: Bundle?
//            ) {
//                config.listener(
//                    CallbackType.REWARD_ARRIVED,
//                    isRewardValid,
//                    rewardType,
//                    extraInfo
//                )
//            }
//
//            /**
//             * - Mintegral GDT Admob广告不存在该回调
//             */
//            override fun onSkippedVideo() {
//                config.listener(
//                    CallbackType.AD_SKIP,
//                    config.codeId,
//                    rewardAd.mediationManager.showEcpm
//                )
//            }
//        })
//        rewardAd.showRewardVideoAd(act)
//    }
//}