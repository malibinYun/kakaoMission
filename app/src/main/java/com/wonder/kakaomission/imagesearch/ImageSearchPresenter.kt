package com.wonder.kakaomission.imagesearch

import com.wonder.kakaomission.network.KakaoApi

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

class ImageSearchPresenter(private var view: ImageSearchContract.View) : ImageSearchContract.Presenter {

    lateinit var kakaoRepository: KakaoApi

    override fun start() {

    }

    override fun requestImageSearch(query: String) {

    }

    override fun requestImageSearch(query: String, page: Int) {

    }
}