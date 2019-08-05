package com.wonder.kakaomission.network

import com.wonder.kakaomission.network.response.ImageSearchResponseDTO
import retrofit2.Call
import retrofit2.http.*

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */
interface KakaoApi {

    @GET("/v2/search/image")
    fun requestImages(
        @Header("Authorization") authorization: String,
        @QueryMap params: Map<String, String>
    ): Call<ImageSearchResponseDTO>
}