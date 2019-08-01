package com.wonder.kakaomission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wonder.kakaomission.R

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

class ImageSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)

        initActionBar()
    }

    private fun initActionBar() {
        actionBar!!.title = "윤혁"
    }
}
