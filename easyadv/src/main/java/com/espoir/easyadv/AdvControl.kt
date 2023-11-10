package com.espoir.easyadv

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.espoir.easyadv.config.BannerAdvConfig
import com.espoir.easyadv.config.FullScreenVideoAdvConfig
import com.espoir.easyadv.config.SplashAdvConfig
import com.bytedance.sdk.openadsdk.stub.activity.Stub_Standard_Portrait_Activity

class AdvControl {

    private var activityCount: Int = 0
    private var showHotSplashAdv = false

    fun showSplashAdv(config: SplashAdvConfig, splashAdvEngine: ISplashAdvEngine?) {
        if (config.userId.isNullOrEmpty()) {
            config.setUserId(EasyAdv.globalConfig()?.userId.orEmpty())
        }
        splashAdvEngine?.showSplashAdv(config)
    }

    fun showFullScreenVideoAdv(config: FullScreenVideoAdvConfig, fullScreenVideoAdvEngine: IFullScreenVideoAdvEngine?) {
        if (config.userId.isNullOrEmpty()) {
            config.setUserId(EasyAdv.globalConfig()?.userId.orEmpty())
        }
        fullScreenVideoAdvEngine?.showFullScreenVideoAdv(config)
    }

    fun showBannerAdv(config: BannerAdvConfig, bannerAdvEngine: IBannerAdvEngine?) {
        if (config.userId.isNullOrEmpty()) {
            config.setUserId(EasyAdv.globalConfig()?.userId.orEmpty())
        }
        bannerAdvEngine?.showBannerAdv(config)
    }

    fun setUpHotSplashAdvStrategy(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
                activityCount++
                if (activity is Stub_Standard_Portrait_Activity) {
                    showHotSplashAdv = false
                }
            }

            override fun onActivityResumed(activity: Activity) {
                Log.i("XIAN","hotSplashAdvStrategy="+ EasyAdv.globalConfig()?.hotSplashAdvStrategy)
                EasyAdv.globalConfig()?.hotSplashAdvStrategy?.let {
                    if (it.showRequirement(activity) && showHotSplashAdv) {
                        showHotSplashAdv = false
                        it.showHotSplashAdv(activity, it.getCodeId())
                    }
                }
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
                activityCount--
                if (activityCount == 0) {// 切换到了后台
                    showHotSplashAdv = true
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }
}