package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.apple.ListAppleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // --------------------------------------- APPLE ----------------------------------------------
    // apple
    @GET("everything")
    suspend fun getApple(
        @Query("q") type: String,
        @Query("apiKey") apiKey: String
    ): ListAppleResponse
    // apple

}