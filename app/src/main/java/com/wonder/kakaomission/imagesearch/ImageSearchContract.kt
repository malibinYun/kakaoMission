package com.wonder.kakaomission.imagesearch

import com.wonder.kakaomission.network.request.ImageSearchRequestDTO
import com.wonder.kakaomission.network.response.ImageSearchDocument
import com.wonder.kakaomission.network.response.Meta

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

interface ImageSearchContract {

    interface View {

        var presenter: Presenter

        fun showConnectFailToast(t: Throwable)

        fun showUnknownErrorToast()

        fun showSearchSuccessToast(totalCount: Int)

        fun initSearchImages(images: List<ImageSearchDocument>)

        fun appendSearchImages(images: List<ImageSearchDocument>, isEnd: Boolean)

    }

    interface Presenter {

        fun start()

        fun requestImageSearch(params: ImageSearchRequestDTO)

        fun requestImageSearch(query: String)

    }
}