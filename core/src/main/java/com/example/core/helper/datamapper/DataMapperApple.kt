package com.example.core.helper.datamapper

import com.example.core.data.source.local.entity.apple.AppleEntity
import com.example.core.data.source.remote.response.apple.AppleResponse
import com.example.core.domain.model.Apple

object DataMapperApple {

    fun mapResponsetoEntities(input: List<AppleResponse>): List<AppleEntity> {
        val appleList = ArrayList<AppleEntity>()
        input.map {
            val apple = AppleEntity(
                id = it.source.id ?: "",
                name = it.source.name ?: "",
                author = it.author ?: "",
                title = it.title ?: "",
                description = it.description ?: "",
                url = it.url ?: "",
                urlToImage = it.urlToImage ?: "",
                publishedAt = it.publishedAt ?: "",
                content = it.content ?: ""
            )
            appleList.add(apple)
        }

        return appleList
    }

    fun mapEntitiestoDomain(input: List<AppleEntity>): List<Apple> =
        input.map {
            Apple(
                id = it.id,
                name = it.name,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                content = it.content
            )
        }

}