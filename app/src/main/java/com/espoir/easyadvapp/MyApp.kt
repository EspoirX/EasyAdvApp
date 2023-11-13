package com.espoir.easyadvapp

import android.app.Application

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

//        EasyAdv.init(this)
//            .sdkConfig {
//                context = this@MyApp
//                appId = "5421722"
//                appName = "EasyAdvApp"
//                userId = "123456"
//                debug = true
//            }
//            .setGlobalAdvConfig {
//                enableHotSplashAdv = true
//                hotSplashAdvStrategy = TTHotSplashAdvStrategy()
//            }
//            .setPlatform(TTAdSdkPlatform())
//            .setSplashAdvEngine(TTSplashAdvEngine())
//            .apply()
    }
}