package com.espoir.easyadv.config

import com.espoir.easyadv.BannerAdvListener
import com.espoir.easyadv.FullScreenVideoAdvListener
import com.espoir.easyadv.IHotSplashAdvStrategy
import com.espoir.easyadv.SplashAdvListener

class GlobalAdvConfig {
    var splashAdvListener: SplashAdvListener? = null
    var fullScreenVideoAdvListener: FullScreenVideoAdvListener? = null
    var bannerAdvListener: BannerAdvListener? = null
    var userId: String = ""
    var enableHotSplashAdv = false
    var hotSplashAdvStrategy: IHotSplashAdvStrategy? = null
}