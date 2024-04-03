package org.bmsk.lifemash.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.bmsk.lifemash.core.data.asDomain
import org.bmsk.lifemash.core.domain.repository.NewsRepository
import org.bmsk.lifemash.core.model.NewsModel
import org.bmsk.lifemash.core.model.section.SbsSection
import org.bmsk.lifemash.core.network.service.NewsClient
import javax.inject.Inject

internal class NewsRepositoryImpl @Inject constructor(
    private val newsClient: NewsClient,
) : NewsRepository {
    override suspend fun getSbsNews(section: SbsSection): Flow<List<NewsModel>> {
        return flow {
            val response = newsClient.getSbsNews(section)
            if (response.isSuccessful) {
                val newsList = (response.body()?.channel?.items ?: emptyList()).asDomain()
                emit(newsList)
            } else {
                emit(emptyList())
            }
        }
    }

    override suspend fun getSbsNews2(section: SbsSection): List<NewsModel> {
        return (newsClient.getSbsNews2(section).channel.items ?: emptyList()).asDomain()
    }

    override suspend fun getGoogleNews(query: String): Flow<List<NewsModel>> {
        return flow {
            val response = newsClient.getGoogleNews(query)
            if (response.isSuccessful) {
                val newsList = response.body()?.channel?.items ?: emptyList()
                emit(newsList.asDomain())
            } else {
                emit(emptyList())
            }
        }
    }
}
