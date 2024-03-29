package com.wonder.kakaomission.ui.imagedetail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.wonder.kakaomission.R
import com.wonder.kakaomission.network.response.ImageSearchDocument
import com.wonder.kakaomission.ui.imagezoom.ImageZoomActivity
import com.wonder.kakaomission.ui.visitweb.VisitWebActivity
import kotlinx.android.synthetic.main.activity_image_detail.*
import org.jetbrains.anko.startActivity

class ImageDetailActivity : AppCompatActivity() {

    private lateinit var imageDocument: ImageSearchDocument

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        getIntentData()
        initView()
    }

    private fun getIntentData() {
        imageDocument = intent.getSerializableExtra("imageDocument") as ImageSearchDocument
    }

    private fun initView() {
        setStatusBarTransparent()
        initImageView()
        initVisitBtn()
        initGoZoomWindowBtn()
    }

    private fun setStatusBarTransparent() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val winParams = window.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
            return
        }
        winParams.flags = winParams.flags and bits.inv()
        window.attributes = winParams
    }

    private fun initImageView() {
        modifyImageHeight()
        insertImage()
        setClickListenerAtImage()
    }

    private fun modifyImageHeight() {
        iv_image_detail_image.layoutParams.height = getDynamicImageHeight()
    }

    private fun insertImage() {
        val imageUrl = imageDocument.image_url
        Glide.with(this)
            .load(imageUrl)
            .into(iv_image_detail_image)
    }

    private fun setClickListenerAtImage() {
        iv_image_detail_image.setOnClickListener {
            startImageZoomActivity()
        }
    }

    private fun initVisitBtn() {
        btn_image_detail_visit.setOnClickListener {
            startActivity<VisitWebActivity>("imageDocument" to imageDocument)
        }
        setVisitButtonText()
    }

    private fun setVisitButtonText() {
        var siteName = imageDocument.display_sitename
        if (siteName.isEmpty()) {
            siteName = "사이트"
        }
        tv_image_detail_visit.text = ("$siteName 방문하기")
    }

    private fun initGoZoomWindowBtn() {
        btn_image_detail_go_zoom_window.setOnClickListener {
            startImageZoomActivity()
        }
    }

    private fun startImageZoomActivity() {
        val imageUrl = imageDocument.image_url
        startActivity<ImageZoomActivity>("imageUrl" to imageUrl)
    }

    private fun getDynamicImageHeight(): Int {
        val width = imageDocument.width.toFloat()
        val height = imageDocument.height.toFloat()
        val heightRatio = height / width
        return (getDeviceWidth() * heightRatio).toInt()
    }

    private fun getDeviceWidth(): Int {
        return resources.displayMetrics.widthPixels
    }
}
