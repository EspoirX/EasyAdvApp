package com.espoir.easyadvapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.espoir.glidedslib.loadImage

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        findViewById<ImageView>(R.id.bgView).loadImage("https://img-blog.csdnimg.cn/3731d157c66a4ab681f78580456f4b4f.png")

        initSplashAdv()
    }

    private fun initSplashAdv() {
//        EasyAdv.splashConfig()
//            .setCodeId("KFC_V_WO_50")
//            .setActivity(this)
//            .setWidth(getScreenWidth())
//            .setHeight(getScreenHeight())
//            .setContainer(findViewById<FrameLayout>(R.id.adLayout))
//            .setSplashAdvListener(onError = { _, _ ->
//                toMain()
//            }, onAdClicked = { _, _ ->
//                toMain()
//            }, onAdSkip = {
//                toMain()
//            }, onAdTimeOver = {
        toMain()
//            })
//            .showSplashAdv()
    }

    private fun toMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}