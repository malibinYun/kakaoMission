package com.wonder.kakaomission.repository

import com.wonder.kakaomission.network.KakaoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created By Yun Hyeok
 * on 8월 04, 2019
 */

object KakaoRepository {

    private val baseUrl = "https://dapi.kakao.com"

    private lateinit var kakaoApiImpl: KakaoApi

    init {
        buildRetrofit()
    }

    fun getInstance(): KakaoApi {
        return kakaoApiImpl
    }

    private fun buildRetrofit() {
        val gsonFactory = GsonConverterFactory.create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonFactory)
            .build()
        kakaoApiImpl = retrofit.create(KakaoApi::class.java)
    }
}