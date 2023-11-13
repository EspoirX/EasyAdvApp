package com.espoir.easyadvapp

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var container: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container = findViewById(R.id.container)
//
//        //显示插屏广告
//        EasyAdv.fullVideoConfig()
//            .setCodeId("KFC_V_WO_50")
//            .setActivity(this)
//            .showFullScreenVideoAdv()
//
//        //banner广告
//        EasyAdv.bannerConfig()
//            .setCodeId("KFC_V_WO_50")
//            .setActivity(this)
//            .showBannerAdv()
//
//        //信息流广告
//        EasyAdv.feedConfig()
//            .setCodeId("KFC_V_WO_50")
//            .setActivity(this)
//            .setContainer(container)
//            .showFeedAdv()
    }
}
