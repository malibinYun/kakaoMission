package com.wonder.kakaomission.ui.imagezoom

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.wonder.kakaomission.R
import kotlinx.android.synthetic.main.activity_image_zoom.*

class ImageZoomActivity : AppCompatActivity() {

    private lateinit var imageUrl: String

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (newProgress == 100) {
                zone_image_zoom_act_loading.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_zoom)

        getIntentData()
        initWindowNoLimits()
        initWebView()
    }

    override fun onBackPressed() {
        if (wv_image_zoom_act_web.canGoBack()) {
            wv_image_zoom_act_web.goBack()
            return
        }
        super.onBackPressed()
    }

    private fun getIntentData() {
        imageUrl = intent.getStringExtra("imageUrl")!!
    }

    private fun initWindowNoLimits() {
        val noLimitsFlag = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        window.setFlags(noLimitsFlag, noLimitsFlag)
    }

    private fun initWebView() {
        wv_image_zoom_act_web.apply {
            webChromeClient = mWebChromeClient
            applyWebViewSettings(this)
            loadUrl(imageUrl)
        }
    }

    private fun applyWebViewSettings(webView: WebView) {
        webView.settings.apply {
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }
    }
}
