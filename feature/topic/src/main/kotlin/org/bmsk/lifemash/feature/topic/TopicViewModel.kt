package org.bmsk.lifemash.feature.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bmsk.lifemash.core.domain.usecase.GetSbsNewsUseCase
import org.bmsk.lifemash.core.domain.usecase.NewsUseCase
import org.bmsk.lifemash.core.model.NewsModel
import org.bmsk.lifemash.core.model.section.SbsSection
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
internal class TopicViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val getSbsNewsUseCase: GetSbsNewsUseCase,
) : ViewModel() {
    private var processingJob: Job? = null

    private val _uiState = MutableStateFlow(
        TopicUiState(
            currentSection = SbsSection.ECONOMICS,
            newsList = persistentListOf(),
        ),
    )
    val uiState = _uiState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    init {
        fetchNews(SbsSection.ECONOMICS)
    }

    fun fetchNews(section: SbsSection) {
        _uiState.update { it.copy(currentSection = section) }
        processNewsFetching { getSbsNewsUseCase(section) }
    }

    fun fetchNewsSearchResults(query: String) {
        processNewsFetching { newsUseCase.getGoogleNews(query).first() }
    }

    private fun processNewsFetching(fetcher: suspend () -> List<NewsModel>) {
        viewModelScope.launch {
            processingJob?.cancel() // 코루틴 작업 취소
            processingJob = launch(Dispatchers.IO) {
                try {
                    val newsList = fetcher().map {
                        async {
                            val document = Jsoup.connect(it.link).get()
                            val imageUrl =
                                document.select("meta[property=og:image]").attr("content")
                            NewsModel(
                                title = it.title,
                                link = it.link,
                                pubDate = it.pubDate,
                                imageUrl = imageUrl,
                            )
                        }
                    }.map { it.await() }
                    _uiState.update { it.copy(newsList = newsList.toPersistentList()) }
                } catch (e: Exception) {
                    _errorFlow.emit(e)
                }
            }
        }
    }
}
