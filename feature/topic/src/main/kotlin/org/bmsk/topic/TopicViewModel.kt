package org.bmsk.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bmsk.data.repository.NewsRepository
import org.bmsk.model.NewsModel
import org.bmsk.model.section.SbsSection
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private val _newsStateFlow = MutableStateFlow<List<NewsModel>>(emptyList())
    val newsStateFlow = _newsStateFlow.asStateFlow()

    init {
        getNews(SbsSection.SECTION_ECONOMICS)
    }

    fun getNews(section: String) {
        viewModelScope.launch {
            val list = newsRepository.getSbsNews(section).first()
            _newsStateFlow.value = list.map { news ->
                async { fetchOpenGraphImage(news) }
            }.awaitAll()
        }
    }

    private suspend fun fetchOpenGraphImage(news: NewsModel): NewsModel =
        withContext(Dispatchers.IO) {
            val jsoup = Jsoup.connect(news.link).get()
            val elements = jsoup.select("meta[property^=og:]")
            val ogImageNode = elements.find { node ->
                node.attr("property") == "og:image"
            }
            news.apply {
                imageUrl = ogImageNode?.attr("content")
            }
        }
}