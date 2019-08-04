package com.wonder.kakaomission.imagesearch

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.wonder.kakaomission.R
import kotlinx.android.synthetic.main.activity_image_search.*
import org.jetbrains.anko.toast


/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

class ImageSearchActivity : AppCompatActivity(), ImageSearchContract.View {

    override lateinit var presenter: ImageSearchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)

        initToolbar()
        initSearchBtn()
    }

    override fun showConnectFailToast(t: Throwable) {
        progressBarOff()
        toast(R.string.server_connect_fail)
        Log.v("Malibin Debug", "t : ${t.message}, stack : ${TextUtils.join("\n", t.stackTrace)}")
    }

    override fun showSearchSuccessToast() {
        progressBarOff()
        toast("")
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_image_search_act) //툴바 등록
        val toolbar = supportActionBar!!

        configureToolbarSettings(toolbar)
        attachCustomLayoutTo(toolbar)
        removePaddingOfToolbar()
    }

    private fun configureToolbarSettings(toolbar: ActionBar) {
        toolbar.setDisplayShowCustomEnabled(true)
        toolbar.setDisplayHomeAsUpEnabled(false)
        toolbar.setDisplayShowTitleEnabled(false)
    }

    private fun attachCustomLayoutTo(toolbar: ActionBar) {
        val matchParent = ActionBar.LayoutParams.MATCH_PARENT
        val layoutParams = ActionBar.LayoutParams(matchParent, matchParent)
        val customView = layoutInflater.inflate(R.layout.action_bar_image_search, null)
        toolbar.setCustomView(customView, layoutParams)
    }

    private fun removePaddingOfToolbar() {
        val toolbar = toolbar_image_search_act
        toolbar.setContentInsetsAbsolute(0, 0)
    }

    private fun initSearchBtn() {
        btn_image_search_act_search.setOnClickListener {
            progressBarOn()
            val keyword = getKeyword()
            presenter.requestImageSearch(keyword)
        }
    }

    private fun getKeyword(): String {
        return et_image_search_act_query.text.toString()
    }

    private fun progressBarOn() {
        progressbar_image_search_act.visibility = View.VISIBLE
    }

    private fun progressBarOff() {
        progressbar_image_search_act.visibility = View.GONE
    }
}
