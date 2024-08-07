package org.bmsk.lifemash.feature.scrap

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.bmsk.lifemash.core.model.NewsModel
import org.bmsk.lifemash.feature.scrap.usecase.DeleteScrapNewsUseCase
import org.bmsk.lifemash.feature.scrap.usecase.GetScrapNewsUseCase
import javax.inject.Inject

@HiltViewModel
internal class ScrapViewModel @Inject constructor(
    private val getScrapNewsUseCase: GetScrapNewsUseCase,
    private val deleteScrapNewsUseCase: DeleteScrapNewsUseCase
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow = _errorFlow.asSharedFlow()

    private val _uiState = MutableStateFlow<ScrapUiState>(ScrapUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val scrapNews = getScrapNewsUseCase()
            Log.e("ScrapViewModel", "scrapNews: $scrapNews")
            _uiState.update { ScrapUiState.Success(scrapNews.toPersistentList()) }
        }
    }

    fun deleteScrapNews(newsModel: NewsModel) = viewModelScope.launch(Dispatchers.IO) {
        val currentState = _uiState.value
        if (currentState !is ScrapUiState.Success) return@launch

        deleteScrapNewsUseCase(newsModel)
        _uiState.update { currentState.copy(scraps = currentState.scraps.remove(newsModel)) }
    }
}