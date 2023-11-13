package com.espoir.easyadvapp.ad

//class TTHotSplashAdvStrategy : IHotSplashAdvStrategy {
//
//    private var lastShowTime = 0L
//    private var ttHotSplashAdvDialog: TTHotSplashAdvDialog? = null
//
//    override fun getCodeId(): String = "KFC_V_WO_50"
//
//    override fun showRequirement(activity: Activity): Boolean {
//        return activity !is SplashActivity
//                && System.currentTimeMillis() - lastShowTime > 60 * 60 * 1000
//    }
//
//    override fun showHotSplashAdv(activity: Activity, codeId: String) {
//        lastShowTime = System.currentTimeMillis()
//        ttHotSplashAdvDialog = TTHotSplashAdvDialog(activity, onShow = { container ->
//            EasyAdv.splashConfig()
//                .setCodeId(codeId)
//                .setActivity(activity)
//                .setWidth(activity.getScreenWidth())
//                .setHeight(activity.getScreenHeight())
//                .setContainer(container)
//                .setSplashAdvListener(onError = { _, _ ->
//                    ttHotSplashAdvDialog?.dismiss()
//                }, onAdClicked = { _, _ ->
//                    ttHotSplashAdvDialog?.dismiss()
//                }, onAdSkip = {
//                    ttHotSplashAdvDialog?.dismiss()
//                }, onAdTimeOver = {
//                    ttHotSplashAdvDialog?.dismiss()
//                })
//                .showSplashAdv()
//        }, onDismiss = {})
//        ttHotSplashAdvDialog?.show()
//    }
//}