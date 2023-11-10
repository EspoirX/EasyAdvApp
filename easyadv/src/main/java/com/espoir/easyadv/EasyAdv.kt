package com.espoir.easyadv

import android.annotation.SuppressLint
import android.app.Application
import com.espoir.easyadv.config.BannerAdvConfig
import com.espoir.easyadv.config.FullScreenVideoAdvConfig
import com.espoir.easyadv.config.GlobalAdvConfig
import com.espoir.easyadv.config.SplashAdvConfig

object EasyAdv {
    private var sInit = false
    private var globalContext: Application? = null

    @SuppressLint("StaticFieldLeak")
    private var advSDKBuilder: AdvSDKBuilder? = null
    private var sdkPlatform: ISdkPlatform? = null
    private var globalAdvConfig: GlobalAdvConfig? = null
    private var splashAdvEngine: ISplashAdvEngine? = null
    private var fullScreenVideoAdvEngine: IFullScreenVideoAdvEngine? = null
    private var bannerAdvEngine: IBannerAdvEngine? = null
    private var advControl: AdvControl? = null
    private var isInitSdkSuccess = false

    const val ERROR_ACT = -3867
    const val ERROR_PARAM = -3868
    const val ERROR_AD_INFO = -3869

    @JvmStatic
    fun init(application: Application) = apply {
        globalContext = application
    }

    @JvmStatic
    fun sdkConfig(options: AdvSDKBuilder.() -> Unit) = apply {
        advSDKBuilder = AdvSDKBuilder().also { options(it) }
    }

    internal fun globalConfig() = globalAdvConfig

    @JvmStatic
    fun setPlatform(sdkPlatform: ISdkPlatform) = apply {
        EasyAdv.sdkPlatform = sdkPlatform
    }

    @JvmStatic
    fun setGlobalAdvConfig(options: GlobalAdvConfig.() -> Unit) = apply {
        globalAdvConfig = GlobalAdvConfig().also { options(it) }
    }

    @JvmStatic
    fun setSplashAdvEngine(engine: ISplashAdvEngine) = apply {
        splashAdvEngine = engine
    }

    @JvmStatic
    fun setFullScreenVideoAdvEngine(engine: IFullScreenVideoAdvEngine) = apply {
        fullScreenVideoAdvEngine = engine
    }

    @JvmStatic
    fun setBannerAdvEngine(engine: IBannerAdvEngine) = apply {
        bannerAdvEngine = engine
    }

    fun apply() {
        if (sInit) return
        advSDKBuilder?.let { builder ->
            sdkPlatform?.initAdvSdk(builder, object : AdvSdkInitCallback {
                override fun onInitSuccess() {
                    isInitSdkSuccess = true
                }

                override fun onInitFail(code: Int, msg: String?) {
                    isInitSdkSuccess = false
                }
            })
            if (advControl == null) {
                advControl = AdvControl()
            }
            if (globalConfig()?.enableHotSplashAdv == true && globalContext != null) {
                advControl?.setUpHotSplashAdvStrategy(globalContext!!)
            }
            sInit = true
        }
    }

    @JvmStatic
    fun splashConfig() = SplashAdvConfig()

    @JvmStatic
    fun fullVideoConfig() = FullScreenVideoAdvConfig()

    @JvmStatic
    fun bannerConfig() = BannerAdvConfig()

    internal fun showSplashAdv(config: SplashAdvConfig) {
        if (!isInitSdkSuccess) return
        advControl?.showSplashAdv(config, splashAdvEngine)
    }

    internal fun showFullScreenVideoAdv(config: FullScreenVideoAdvConfig) {
        if (!isInitSdkSuccess) return
        advControl?.showFullScreenVideoAdv(config, fullScreenVideoAdvEngine)
    }

    internal fun showBannerAdv(config: BannerAdvConfig) {
        if (!isInitSdkSuccess) return
        advControl?.showBannerAdv(config, bannerAdvEngine)
    }
}

class Test {
//    fun init(application: Application) {
//        //初始化
//        EasyAdv
//            .init(application)
//            .sdkConfig {
//            }
//            .setGlobalAdvConfig {
//            }
//            .setPlatform()
//            .setSplashAdvEngine()
//            .apply()
//        //使用
//        EasyAdv.splashConfig()
//            .setUserId("")
//            .setCodeId("")
//            .setSplashAdvListener { }
//            .showSplashAdv()
//    }
}