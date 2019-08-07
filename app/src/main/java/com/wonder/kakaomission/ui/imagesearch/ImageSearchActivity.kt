package com.wonder.kakaomission.ui.imagesearch

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import com.wonder.kakaomission.R
import com.wonder.kakaomission.ui.imagesearch.adapter.ImageSearchRecyclerViewAdapter
import com.wonder.kakaomission.network.request.ImageSearchRequestDTO
import com.wonder.kakaomission.network.response.ImageSearchDocument
import kotlinx.android.synthetic.main.activity_image_search.*
import org.jetbrains.anko.toast
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.math.abs

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

class ImageSearchActivity : AppCompatActivity(), ImageSearchContract.View {
    private var keyword = ""
    private var currentPageCount = 1
    private var isEndPage = false

    override lateinit var presenter: ImageSearchContract.Presenter
    private lateinit var rvAdapter: ImageSearchRecyclerViewAdapter

    private val scrollListener =
        NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            val isBottom = scrollY == abs(v.measuredHeight - v.getChildAt(0).measuredHeight)
            if (isBottom && !isEndPage) {
                turnOnImageMoreLoadProgressBar()
                moreImageLoad()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)

        Log.d("Malibin Debug", getKeyHash(this))

        initPresenter()
        initView()
    }

    override fun showConnectFailToast(t: Throwable) {
        turnOffAllProgressBar()
        toast(R.string.server_connect_fail)
        Log.v("Malibin Debug", "t.message : ${t.message}, stack : ${TextUtils.join("\n", t.stackTrace)}")
    }

    override fun showUnknownErrorToast() {
        turnOffAllProgressBar()
        toast(R.string.unknown_error)
    }

    override fun showSearchSuccessToast(totalCount: Int) {
        turnOffAllProgressBar()
        toast("총 $totalCount 건의 이미지가 검색되었습니다.")
    }

    override fun initSearchImages(images: List<ImageSearchDocument>) {
        keyword = getKeyword()
        currentPageCount = 1
        rvAdapter.initFirstSearch(images)
    }

    override fun appendSearchImages(images: List<ImageSearchDocument>, isEnd: Boolean) {
        turnOffAllProgressBar()
        currentPageCount++
        rvAdapter.addItems(images)
        isEndPage = isEnd
    }

    private fun initPresenter() {
        presenter = ImageSearchPresenter(this).apply { start() }
    }

    private fun initView() {
        initToolbar()
        initEditTextBtn()
        initSearchBtn()
        initImageList()
        initScrollView()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_image_search_act)
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

    private fun initEditTextBtn() {
        et_image_search_act_query.setOnEditorActionListener { textView, actionId, _ ->
            if (textView.text.isEmpty()) {
                toast(R.string.keyword_required)
                return@setOnEditorActionListener false
            }
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                sendSearchRequest()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun initSearchBtn() {
        btn_image_search_act_search.setOnClickListener {
            val keyword = getKeyword()
            if (keyword.isEmpty()) {
                toast(R.string.keyword_required)
                return@setOnClickListener
            }
            sendSearchRequest()
        }
    }

    private fun sendSearchRequest() {
        val keyword = getKeyword()
        turnOnSearchProgressBar()
        et_image_search_act_query.onEditorAction(EditorInfo.IME_ACTION_DONE)
        presenter.requestImageSearch(keyword)
    }

    private fun getKeyword(): String {
        return et_image_search_act_query.text.toString()
    }

    private fun turnOnSearchProgressBar() {
        progressbar_image_search_act.visibility = View.VISIBLE
    }

    private fun turnOnImageMoreLoadProgressBar() {
        progressbar_image_search_act_more_load.visibility = View.VISIBLE
    }

    private fun turnOffAllProgressBar() {
        progressbar_image_search_act.visibility = View.GONE
        progressbar_image_search_act_more_load.visibility = View.GONE
    }

    private fun initImageList() {
        rvAdapter = ImageSearchRecyclerViewAdapter(this)
        rv_image_search_act_result.apply {
            adapter = rvAdapter
            layoutManager = GridLayoutManager(this@ImageSearchActivity, 2)
        }
    }

    private fun moreImageLoad() {
        val nextPage = currentPageCount + 1
        val params = ImageSearchRequestDTO(keyword, page = nextPage)
        presenter.requestImageSearch(params)
    }

    private fun initScrollView() {
        nscroll_image_search_act.setOnScrollChangeListener(scrollListener)
    }

    private fun getKeyHash(context: Context): String? {
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
