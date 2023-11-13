package com.espoir.easyadv

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.bytedance.sdk.openadsdk.stub.activity.Stub_Standard_Portrait_Activity
import com.espoir.easyadv.config.BannerAdvConfig
import com.espoir.easyadv.config.FeedAdConfig
import com.espoir.easyadv.config.FullScreenVideoAdvConfig
import com.espoir.easyadv.config.RewardVideoAdvConfig
import com.espoir.easyadv.config.SplashAdvConfig
import com.espoir.easyadv.config.listener
import com.espoir.easyadv.interceptor.AdvInterceptCallback
import com.espoir.easyadv.interceptor.AdvInterceptorManager

class AdvControl {

    private var interceptorManager = AdvInterceptorManager()
    private var activityCount: Int = 0
    private var showHotSplashAdv = false

    @Volatile
    private var splashAdvFlag = false

    @Volatile
    private var showSplashAdvTime = 0L


    fun showSplashAdv(config: SplashAdvConfig, splashAdvEngine: ISplashAdvEngine?) {
        if (config.userId.isNullOrEmpty()) {
            config.setUserId(EasyAdv.globalConfig()?.userId.orEmpty())
        }
        if (EasyAdv.isFinishSdkInit && !EasyAdv.isInitSdkSuccess) { //sdk初始化失败。回调失败
            config.listener(CallbackType.ERROR, EasyAdv.ERROR_SDK, "sdk init fail")
            return
        }
        showSplashAdvTime = System.currentTimeMillis()
        //等待sdk初始化完成
        while (!splashAdvFlag) {
            if (EasyAdv.isFinishSdkInit) {
                splashAdvFlag = if (EasyAdv.isInitSdkSuccess) {
                    splashAdvEngine?.showSplashAdv(config)
                    true
                } else {
                    config.listener(CallbackType.ERROR, EasyAdv.ERROR_SDK, "sdk init fail")
                    true
                }
            } else {
                if (EasyAdv.sdkTimeOutMillis > 0) {
                    if (System.currentTimeMillis() - showSplashAdvTime > EasyAdv.sdkTimeOutMillis) {
                        config.listener(CallbackType.ERROR, EasyAdv.ERROR_SDK, "sdk init time out")
                        splashAdvFlag = true
                    }
                }
            }
        }
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

    fun showFeedAdv(config: FeedAdConfig, feedAdvEngine: IFeedAdvEngine?) {
        if (config.userId.isNullOrEmpty()) {
            config.setUserId(EasyAdv.globalConfig()?.userId.orEmpty())
        }
        feedAdvEngine?.showFeedAdv(config)
    }

    fun showRewardVideoAdv(config: RewardVideoAdvConfig, rewardVideoEngine: IRewardVideoAdvEngine?) {
        if (config.userId.isNullOrEmpty()) {
            config.setUserId(EasyAdv.globalConfig()?.userId.orEmpty())
        }
        if (config.interceptors.size > 0) {
            interceptorManager.attachInterceptors(config.interceptors)
            interceptorManager.handlerInterceptor(config.extraMap, object : AdvInterceptCallback {
                override fun onNext(map: HashMap<String, String>?) {
                    rewardVideoEngine?.showRewardVideoAdv(config)
                }

                override fun onInterrupt(msg: String?) {
                    config.listener(CallbackType.ERROR, -1, msg)
                }
            })
        } else {
            rewardVideoEngine?.showRewardVideoAdv(config)
        }
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
                if (EasyAdv.isFinishSdkInit && EasyAdv.isInitSdkSuccess) {
                    EasyAdv.globalConfig()?.hotSplashAdvStrategy?.let {
                        if (it.showRequirement(activity) && showHotSplashAdv) {
                            showHotSplashAdv = false
                            it.showHotSplashAdv(activity, it.getCodeId())
                        }
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