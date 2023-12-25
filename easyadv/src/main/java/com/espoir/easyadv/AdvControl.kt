package com.espoir.easyadv

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.bytedance.sdk.openadsdk.stub.activity.Stub_Standard_Portrait_Activity
import com.espoir.easyadv.config.BannerAdvConfig
import com.espoir.easyadv.config.FeedAdConfig
import com.espoir.easyadv.config.FullScreenVideoAdvConfig
import com.espoir.easyadv.config.RewardVideoAdvConfig
import com.espoir.easyadv.config.SplashAdvConfig
import com.espoir.easyadv.config.listener
import com.espoir.easyadv.interceptor.AdvInterceptCallback
import com.espoir.easyadv.interceptor.AdvInterceptorManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

class AdvControl : LifecycleObserver {

    private var interceptorManager = AdvInterceptorManager()
    private var showHotSplashAdv = false

    @Volatile
    private var splashAdvFlag = AtomicBoolean(false)

    @Volatile
    private var showSplashAdvTime = 0L
    private var isForeground = false
    private var currAct: Activity? = null


    fun showSplashAdv(config: SplashAdvConfig, splashAdvEngine: ISplashAdvEngine?) {
        if (config.userId.isNullOrEmpty()) {
            config.setUserId(EasyAdv.globalConfig()?.userId.orEmpty())
        }
        if (EasyAdv.isFinishSdkInit && !EasyAdv.isInitSdkSuccess) { //sdk初始化失败。回调失败
            config.listener(CallbackType.ERROR, EasyAdv.ERROR_SDK, "sdk init fail")
            return
        }
        if (config.isHotSplash) {
            splashAdvEngine?.showSplashAdv(config)
            return
        }
        showSplashAdvTime = System.currentTimeMillis()
        //等待sdk初始化完成
        config.scope?.launch(Dispatchers.IO) {
            while (!splashAdvFlag.get()) {
                if (EasyAdv.isFinishSdkInit) {
                    withContext(Dispatchers.Main) {
                        if (EasyAdv.isInitSdkSuccess) {
                            splashAdvEngine?.showSplashAdv(config)
                        } else {
                            config.listener(CallbackType.ERROR, EasyAdv.ERROR_SDK, "sdk init fail")
                        }
                    }
                    splashAdvFlag.set(true)
                } else {
                    if (EasyAdv.sdkTimeOutMillis > 0) {
                        if (System.currentTimeMillis() - showSplashAdvTime > EasyAdv.sdkTimeOutMillis) {
                            withContext(Dispatchers.Main) {
                                config.listener(CallbackType.ERROR, EasyAdv.ERROR_SDK, "sdk init time out")
                            }
                            splashAdvFlag.set(true)
                        }
                    } else {
                        splashAdvFlag.set(true)
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

    //在前台
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onForeground() {
        if (!isForeground) {
            isForeground = true

            Log.i("AdvControl", "App on onForeground")
            currAct?.let { act ->
                if (act is Stub_Standard_Portrait_Activity) {
                    showHotSplashAdv = false
                }
                if (EasyAdv.isFinishSdkInit && EasyAdv.isInitSdkSuccess) {
                    EasyAdv.globalConfig()?.hotSplashAdvStrategy?.let {
                        if (it.showRequirement(act) && showHotSplashAdv) {
                            showHotSplashAdv = false
                            Log.i("AdvControl", "==热启动显示广告==")
                            it.showHotSplashAdv(act, it.getCodeId())
                        }
                    }
                }
            }
        }
    }

    //在后台
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onBackground() {
        if (isForeground) {
            isForeground = false
        }
        showHotSplashAdv = true
        Log.i("AdvControl", "App on onBackground")
    }

    fun setUpHotSplashAdvStrategy(application: Application) {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                currAct = activity
            }

            override fun onActivityStarted(activity: Activity) {
                currAct = activity
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                currAct = null
            }
        })
    }
}