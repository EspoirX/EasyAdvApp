package com.espoir.easyadvapp.ad

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.espoir.easyadvapp.R

class TTHotSplashAdvDialog(
    context: Context,
    private val onShow: (container: ViewGroup) -> Unit,
    private val onDismiss: () -> Unit,
) : Dialog(
    context,
    android.R.style.Theme_Black_NoTitleBar_Fullscreen
),
    DialogInterface.OnDismissListener,
    DialogInterface.OnCancelListener {

    init {
        window?.let {
            it.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            it.decorView.setPadding(0, 0, 0, 0)
            val layoutParams = it.attributes
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // 延伸显示区域到刘海
                val lp = it.attributes
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                it.attributes = lp
                // 设置页面全屏显示
                val decorView = it.decorView
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
            it.attributes = layoutParams
        }
    }

    private var adLayout: FrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setCanceledOnTouchOutside(false)
        setOnDismissListener(this)
        setOnCancelListener(this)
        adLayout = findViewById(R.id.adLayout)
    }

    override fun show() {
        super.show()
        runCatching {
            adLayout?.let { onShow.invoke(it) }
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        onDismiss.invoke()
    }

    override fun onCancel(dialog: DialogInterface?) {
        onDismiss.invoke()
    }
}