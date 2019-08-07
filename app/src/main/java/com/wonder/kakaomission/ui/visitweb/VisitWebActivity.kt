package com.wonder.kakaomission.ui.visitweb

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wonder.kakaomission.R
import com.wonder.kakaomission.network.response.ImageSearchDocument
import kotlinx.android.synthetic.main.activity_visit_web.*

class VisitWebActivity : AppCompatActivity() {

    private lateinit var imageDocument: ImageSearchDocument

    private val mWebViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            progressbar_visit_web_act.visibility = View.VISIBLE
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            progressbar_visit_web_act.progress = newProgress
            if (newProgress == 100) {
                progressbar_visit_web_act.visibility = View.GONE
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
        if (wv_visit_web_act_web.canGoBack()) {
            wv_visit_web_act_web.goBack()
            return
        }
        super.onBackPressed()
    }


    private fun getIntentData() {
        imageDocument = intent.getSerializableExtra("imageDocument") as ImageSearchDocument
    }

    private fun initView() {
        initTopBar()
        initWebView()
    }

    private fun initTopBar() {
        btn_visit_web_act_close.setOnClickListener { finish() }
        tv_visit_web_act_store_name.text = imageDocument.display_sitename
    }

    private fun initWebView() {
        val siteUrl = imageDocument.doc_url
        wv_visit_web_act_web.apply {
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
}
