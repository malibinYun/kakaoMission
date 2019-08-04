package com.wonder.kakaomission.imagesearch

import android.util.Log
import com.wonder.kakaomission.R
import com.wonder.kakaomission.network.KakaoApi
import com.wonder.kakaomission.network.response.ImageSearchResponseDTO
import com.wonder.kakaomission.repository.KakaoRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

class ImageSearchPresenter(private var view: ImageSearchContract.View) : ImageSearchContract.Presenter {

    lateinit var kakaoRepository: KakaoApi

    override fun start() {
        kakaoRepository = KakaoRepository.getInstance()
    }

    override fun requestImageSearch(query: String) {
        kakaoRepository.requestImages(
            "KakaoAK 744140a965961a3b7748073f58d4064f",
            query,
            "accuracy",
            1,
            20
        ).enqueue(object : Callback<ImageSearchResponseDTO> {
            override fun onFailure(call: Call<ImageSearchResponseDTO>, t: Throwable) {
                view.showConnectFailToast(t)
            }

            override fun onResponse(call: Call<ImageSearchResponseDTO>, response: Response<ImageSearchResponseDTO>) {

                Log.d("Malibin Debug", "response.raw() : ${response.raw()}")

                response.body()?.let {
                    Log.d("Malibin Debug", "response.body().toString() : ${response.body().toString()}")
                }
                if (response.isSuccessful) {
                    view.showSearchSuccessToast()
                    return
                }
                val res = response.errorBody()!!
                Log.d("Malibin Debug", "response.errorBody()!!.string() : ${res.string()}")
                Log.d("Malibin Debug", "res.source().string() : ${res.source().toString()}")
                Log.d("Malibin Debug", "res.contentType().string() : ${res.contentType()}")

            }
        })
    }

    override fun requestImageSearch(query: String, page: Int) {

    }
}