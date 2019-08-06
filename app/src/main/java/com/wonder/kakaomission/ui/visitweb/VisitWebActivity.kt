package com.wonder.kakaomission.ui.visitweb

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import com.wonder.kakaomission.R
import kotlinx.android.synthetic.main.activity_visit_web.*

class VisitWebActivity : AppCompatActivity() {
    private lateinit var siteUrl: String
    private lateinit var siteTitle: String

    private val mWebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            progressbar_store_web_act.visibility = View.VISIBLE
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            progressbar_store_web_act.progress = newProgress
            if (newProgress == 100) {
                progressbar_store_web_act.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit_web)

        getIntentData()
        initView()
    }

    override fun onBackPressed() {
        if (wv_store_web_act_web.canGoBack()) {
            wv_store_web_act_web.goBack()
            return
        }
        super.onBackPressed()
    }

    private fun getIntentData() {
        siteUrl = intent.getStringExtra("siteUrl")!!
        siteTitle = intent.getStringExtra("siteTitle")!!
    }

    private fun initView() {
        initTopBar()
        initWebView()
        initProgressBar()
    }

    private fun initTopBar() {
        btn_store_web_act_close.setOnClickListener {
            finish()
        }

        tv_store_web_act_store_name.text = siteTitle
    }

    private fun initWebView() {
        wv_store_web_act_web.apply {
            webViewClient = mWebViewClient
            webChromeClient = mWebChromeClient
            applyWebViewSettings(this)
            loadUrl(siteUrl)
        }
    }

    private fun applyWebViewSettings(webView: WebView) {
        webView.settings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            setSupportZoom(false)
            builtInZoomControls = false
        }
    }

    private fun initProgressBar() {
        val color = ContextCompat.getColor(this, R.color.colorPrimary)
        progressbar_store_web_act.progressDrawable.setColorFilter(
            color, android.graphics.PorterDuff.Mode.SRC_IN
        )
    }
}
