package com.example.core.data.source.remote

import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.apple.AppleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService : ApiService
) {

    private val tag = RemoteDataSource::class.java.simpleName.toString()


    // --------------------------------------- Apple ----------------------------------------------
    suspend fun getApple(
        type : String,
        apiKey : String
    ): Flow<ApiResponse<List<AppleResponse>>> {
        return flow {
            try {
                val response = apiService.getApple(type,apiKey)
                val dataArray = response.data
                Timber.d("check value source = ${response.data}")
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}