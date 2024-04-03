package org.bmsk.lifemash.core.network.service

import org.bmsk.lifemash.core.model.section.SbsSection
import org.bmsk.lifemash.core.network.model.NewsRss
import retrofit2.Response
import javax.inject.Inject

interface NewsClient {
    suspend fun getSbsNews(
        section: SbsSection = SbsSection.ECONOMICS,
        plink: String = "RSSREADER",
    ): Response<NewsRss>

    suspend fun getSbsNews2(
        section: SbsSection = SbsSection.ECONOMICS,
        plink: String = "RSSREADER",
    ): NewsRss

    suspend fun getGoogleNews(
        query: String,
    ): Response<NewsRss>
}

internal class NewsClientImpl @Inject constructor(
    private val googleNewsService: GoogleNewsService,
    private val sbsNewsService: SbsNewsService,
) : NewsClient {
    override suspend fun getSbsNews(
        section: SbsSection,
        plink: String,
    ): Response<NewsRss> {
        return sbsNewsService.getNews(
            sectionId = section.id,
            plink = plink,
        )
    }

    override suspend fun getSbsNews2(
        section: SbsSection,
        plink: String,
    ): NewsRss {
        return sbsNewsService.getNews2(
            sectionId = section.id,
            plink = plink,
        )
    }

    override suspend fun getGoogleNews(
        query: String,
    ): Response<NewsRss> {
        return googleNewsService.search(
            query = query,
        )
    }
}
