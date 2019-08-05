package com.wonder.kakaomission.network.request

/**
 * Created By Yun Hyeok
 * on 8월 05, 2019
 */

data class ImageSearchRequestDTO(
    val query: String,
    var sort: String = "accuracy",
    var page: Int = 1,
    val size: Int = 25
) {
    fun toMap(): Map<String, String> {
        val result = HashMap<String, String>()
        result["query"] = query
        result["sort"] = sort
        result["page"] = page.toString()
        result["size"] = size.toString()
        return result
    }
}