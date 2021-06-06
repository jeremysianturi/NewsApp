package com.example.core.data.repository

import com.example.core.data.NetworkBoundResourceWithDeleteLocalData
import com.example.core.data.Resource
import com.example.core.data.source.local.room.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.apple.AppleResponse
import com.example.core.domain.model.Apple
import com.example.core.domain.repository.IAppleRepository
import com.example.core.helper.datamapper.DataMapperApple
import com.example.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppleRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IAppleRepository {

    override fun getApple(type: String, apiKey: String): Flow<Resource<List<Apple>>> =
        object :
            NetworkBoundResourceWithDeleteLocalData<List<Apple>, List<AppleResponse>>() {

            override fun loadFromDB(): Flow<List<Apple>> {
                return localDataSource.getApple().map {
                    DataMapperApple.mapEntitiestoDomain(it)
                }
            }

            override fun shouldFetch(data: List<Apple>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<AppleResponse>>> =
                remoteDataSource.getApple(type,apiKey)

            override suspend fun saveCallResult(data: List<AppleResponse>) {
                val list = DataMapperApple.mapResponsetoEntities(data)
                localDataSource.insertApple(list)
            }

            override suspend fun emptyDataBase() {
            }

//            override suspend fun emptyDataBase() {
//                localDataSource.deleteApple()
//            }

        }.asFlow()

    override fun getSearchApple(search: String): Flow<List<Apple>> {
        return localDataSource.getSearchApple(search).map {
            DataMapperApple.mapEntitiestoDomain(it)
        }
    }

}