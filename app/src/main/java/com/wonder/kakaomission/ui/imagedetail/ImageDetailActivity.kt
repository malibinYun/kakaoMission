package com.wonder.kakaomission.ui.imagedetail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.wonder.kakaomission.R
import com.wonder.kakaomission.network.response.ImageSearchDocument
import com.wonder.kakaomission.ui.visitweb.VisitWebActivity
import kotlinx.android.synthetic.main.activity_image_detail.*
import org.jetbrains.anko.startActivity

class ImageDetailActivity : AppCompatActivity() {

    private lateinit var imageDocument: ImageSearchDocument

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)


        getIntentData()
        Log.d("Malibin Debug", "imageDocument : $imageDocument")
        initView()
    }

    private fun getIntentData() {
        imageDocument = intent.getSerializableExtra("imageDocument") as ImageSearchDocument
    }

    private fun initView() {
        setStatusBarTransparent()
        initImage()
        initVisitBtn()
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

    private fun initImage() {
        Log.d("Malibin Debug", "getDynamicImageHeight() : ${getDynamicImageHeight()}")
        ll_image_detail_image_frame.layoutParams.height = getDynamicImageHeight()
        //iv_image_detail_image.layoutParams.height = getDynamicImageHeight()
        val imageUrl = imageDocument.image_url
        Glide.with(this)
            .load(imageUrl)
            .into(iv_image_detail_image)
    }

    private fun initVisitBtn() {
        btn_image_detail_visit.setOnClickListener {
            Log.d("Malibin Debug", "iv_image_detail_image.height : ${iv_image_detail_image.height}")
            startActivity<VisitWebActivity>("imageDocument" to imageDocument)
        }
        var siteName = imageDocument.display_sitename
        if (siteName.isEmpty()) {
            siteName = "사이트"
        }
        tv_image_detail_visit.text = ("$siteName 방문하기")
    }

    private fun getDynamicImageHeight(): Int {
        val width = imageDocument.width.toFloat()
        val height = imageDocument.height.toFloat()

        var heightRatio = height / width
//        if (width > height) {
//            heightRatio = width / height
//        }
        Log.d(
            "Malibin Debug",
            "width : $width, height : $height, ratio = $heightRatio getDeviceWidth() : ${getDeviceWidth()}, result : ${getDeviceWidth() * heightRatio}"
        )
        return (getDeviceWidth() * heightRatio).toInt()
    }

    private fun getDeviceWidth(): Int {
        return resources.displayMetrics.widthPixels
    }

    private fun px2dp(px: Int): Float {
        return px / ((resources.displayMetrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT)
    }
}
