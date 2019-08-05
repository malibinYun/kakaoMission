package com.wonder.kakaomission.imagesearch

import android.util.Log
import com.wonder.kakaomission.network.KakaoApi
import com.wonder.kakaomission.network.request.ImageSearchRequestDTO
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

    override fun requestImageSearch(params: ImageSearchRequestDTO) {
        kakaoRepository.requestImages(getAuthKey(), params.toMap()).enqueue(object : Callback<ImageSearchResponseDTO> {
            override fun onFailure(call: Call<ImageSearchResponseDTO>, t: Throwable) {
                view.showConnectFailToast(t)
            }

            override fun onResponse(call: Call<ImageSearchResponseDTO>, response: Response<ImageSearchResponseDTO>) {
                Log.d("Malibin Debug", "response.raw() : ${response.raw()}")
                if (response.isSuccessful) {
                    val metaData = response.body()!!.meta
                    //view.showSearchSuccessToast(metaData)
                    return
                }
                val res = response.errorBody()!!
                Log.d("Malibin Debug", "response.errorBody()!!.string() : ${res.string()}")
                Log.d("Malibin Debug", "res.source().string() : ${res.source().toString()}")
                Log.d("Malibin Debug", "res.contentType().string() : ${res.contentType()}")
                view.showUnknownErrorToast()
            }
        })
    }

    override fun requestImageSearch(query: String) {
        val params = ImageSearchRequestDTO(query = query).toMap()
        kakaoRepository.requestImages(getAuthKey(), params).enqueue(object : Callback<ImageSearchResponseDTO> {
            override fun onFailure(call: Call<ImageSearchResponseDTO>, t: Throwable) {
                view.showConnectFailToast(t)
            }

            override fun onResponse(call: Call<ImageSearchResponseDTO>, response: Response<ImageSearchResponseDTO>) {
                if (response.isSuccessful) {
                    val data = response.body()!!
                    val totalCount = data.meta.pageable_count
                    val isEnd = data.meta.is_end
                    val images = data.documents
                    view.showSearchSuccessToast(totalCount)
                    view.appendSearchImages(images, isEnd)
                    return
                }
                view.showUnknownErrorToast()
            }
        })
    }

    private fun getAuthKey(): String {
        return "KakaoAK 744140a965961a3b7748073f58d4064f"
    }
}