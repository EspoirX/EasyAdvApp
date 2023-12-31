package com.espoir.easyadv

import com.espoir.easyadv.config.BannerAdvConfig
import com.espoir.easyadv.config.FeedAdConfig
import com.espoir.easyadv.config.FullScreenVideoAdvConfig
import com.espoir.easyadv.config.RewardVideoAdvConfig
import com.espoir.easyadv.config.SplashAdvConfig

interface ISplashAdvEngine {
    fun showSplashAdv(config: SplashAdvConfig)
}

interface IFullScreenVideoAdvEngine {
    fun showFullScreenVideoAdv(config: FullScreenVideoAdvConfig)
}

interface IBannerAdvEngine {
    fun showBannerAdv(config: BannerAdvConfig)
}

interface IFeedAdvEngine {
    fun showFeedAdv(config: FeedAdConfig)
}

interface IRewardVideoAdvEngine {
    fun showRewardVideoAdv(config: RewardVideoAdvConfig)
}