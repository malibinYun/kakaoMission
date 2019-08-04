package com.wonder.kakaomission.network

import com.wonder.kakaomission.network.response.ImageSearchResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */
interface KakaoApi {

    @GET("/v2/search/image")
    fun requestImages(
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<ImageSearchResponseDTO>


    //쿼리랑 헤더 줄일수 있는 방법 생각해 볼 것
}