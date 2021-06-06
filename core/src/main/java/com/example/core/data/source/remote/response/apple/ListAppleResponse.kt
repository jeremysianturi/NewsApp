package com.example.core.data.source.remote.response.apple

import com.google.gson.annotations.SerializedName

data class ListAppleResponse (

    @field:SerializedName("articles")
    val data: List<AppleResponse>

        )