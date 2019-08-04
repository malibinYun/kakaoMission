package com.wonder.kakaomission.imagesearch

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

interface ImageSearchContract {

    interface View {

        var presenter: Presenter

        fun showConnectFailToast(t: Throwable)

        fun showSearchSuccessToast()

    }

    interface Presenter {

        fun start()

        fun requestImageSearch(query: String)

        fun requestImageSearch(query: String, page: Int)

    }
}