package com.wonder.kakaomission.imagesearch

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.wonder.kakaomission.R
import com.wonder.kakaomission.imagesearch.adapter.ImageSearchRecyclerViewAdapter
import com.wonder.kakaomission.network.request.ImageSearchRequestDTO
import com.wonder.kakaomission.network.response.ImageSearchDocument
import com.wonder.kakaomission.network.response.Meta
import kotlinx.android.synthetic.main.activity_image_search.*
import org.jetbrains.anko.toast
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

class ImageSearchActivity : AppCompatActivity(), ImageSearchContract.View {

    override lateinit var presenter: ImageSearchContract.Presenter

    private lateinit var rvAdapter : ImageSearchRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)

        Log.v("Malibin Debug", getKeyHash(this))

        initPresenter()
        initToolbar()
        initSearchBtn()
        initImageList()
    }

    override fun showConnectFailToast(t: Throwable) {
        progressBarOff()
        toast(R.string.server_connect_fail)
        Log.v("Malibin Debug", "t.message : ${t.message}, stack : ${TextUtils.join("\n", t.stackTrace)}")
    }

    override fun showUnknownErrorToast() {
        progressBarOff()
        toast("알 수 없는 에러가 발생하였습니다.")
    }

    override fun showSearchSuccessToast(totalCount: Int) {
        progressBarOff()
        toast("총 $totalCount 건의 이미지가 검색되었습니다.")
    }

    override fun appendSearchImages(images: List<ImageSearchDocument>, isEnd: Boolean) {

    }

    private fun initPresenter() {
        presenter = ImageSearchPresenter(this).apply { start() }
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
            val requestParams = ImageSearchRequestDTO(query = keyword)
            presenter.requestImageSearch(requestParams)
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

    private fun initImageList() {
        rvAdapter = ImageSearchRecyclerViewAdapter(this)
        rv_image_search_act_result.apply{
            adapter = rvAdapter
            layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        }
    }

    fun getKeyHash(context: Context): String? {
        val packageInfo =
            packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES) ?: return null

        for (signature in packageInfo.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
            } catch (e: NoSuchAlgorithmException) {
                Log.w("Malibin Debug", "Unable to get MessageDigest. signature=$signature", e)
            }

        }
        return null
    }
}
