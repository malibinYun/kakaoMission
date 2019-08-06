package com.wonder.kakaomission.ui.imagesearch

import com.wonder.kakaomission.network.request.ImageSearchRequestDTO
import com.wonder.kakaomission.network.response.ImageSearchDocument
import com.wonder.kakaomission.util.BasePresenter
import com.wonder.kakaomission.util.BaseView

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

interface ImageSearchContract {

    interface View : BaseView<Presenter> {

        fun showConnectFailToast(t: Throwable)

        fun showUnknownErrorToast()

        fun showSearchSuccessToast(totalCount: Int)

        fun initSearchImages(images: List<ImageSearchDocument>)

        fun appendSearchImages(images: List<ImageSearchDocument>, isEnd: Boolean)

    }

    interface Presenter : BasePresenter {

        fun requestImageSearch(params: ImageSearchRequestDTO)

        fun requestImageSearch(query: String)

    }
}