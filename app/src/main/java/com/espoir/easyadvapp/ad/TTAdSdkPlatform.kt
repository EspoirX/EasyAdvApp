package com.espoir.easyadvapp.ad

//class TTAdSdkPlatform : ISdkPlatform {
//    override fun initAdvSdk(builder: AdvSDKBuilder, callback: AdvSdkInitCallback) {
//        TTAdSdk.init(
//            builder.context, TTAdConfig.Builder()
//                .appId(builder.appId)
//                .useTextureView(true)
//                .appName(builder.appName)
//                .supportMultiProcess(true)
//                .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_NO_TITLE_BAR)
//                .allowShowNotify(false)
//                .useMediation(true)
//                .setMediationConfig(
//                    MediationConfig.Builder()
//                        .setMediationConfigUserInfoForSegment(
//                            object : MediationConfigUserInfoForSegment() {
//                                override fun getUserId(): String {
//                                    return builder.userId.orEmpty()
//                                }
//                            }).build()
//                )
//                .debug(builder.debug)
//                .build()
//        )
//        TTAdSdk.start(object : TTAdSdk.Callback {
//            override fun success() {
//                callback.onInitSuccess()
//            }
//
//            override fun fail(code: Int, msg: String?) {
//                callback.onInitFail(code, msg)
//            }
//        })
//    }
//}