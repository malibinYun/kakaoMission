package com.wonder.kakaomission.repository

import com.wonder.kakaomission.network.KakaoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created By Yun Hyeok
 * on 8월 04, 2019
 */

class KakaoRepository private constructor() {

    companion object {
        private val baseUrl = "http://dapi.kakao.com"
        private var instance: KakaoRepository? = null
        private var kakaoApiImpl: KakaoApi? = null

        fun getInstance(): KakaoApi {
            return kakaoApiImpl
                ?: getKakaoApiImpl()
        }

        private fun buildRetrofit() {
            val gsonFactory = GsonConverterFactory.create()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonFactory)
                .build()
            kakaoApiImpl = retrofit.create(KakaoApi::class.java)
        }

        private fun getKakaoApiImpl(): KakaoApi {
            instance = KakaoRepository().apply { instance = this }
            buildRetrofit()
            return kakaoApiImpl!!
        }
    }


}