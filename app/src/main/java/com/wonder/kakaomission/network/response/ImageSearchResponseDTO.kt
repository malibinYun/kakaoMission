package com.wonder.kakaomission.network.response

/**
 * Created By Yun Hyeok
 * on 8월 02, 2019
 */

data class ImageSearchResponseDTO(
    val meta: Meta,
    val documents: List<ImageSearchDocument>
)