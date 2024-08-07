package org.bmsk.lifemash.feature.scrap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest

internal object ScrapRoute {
    const val ROUTE = "scrap"

    @Composable
    operator fun invoke(
        onClickNews: (url: String) -> Unit,
        onShowErrorSnackbar: (Throwable?) -> Unit,
        viewModel: ScrapViewModel = hiltViewModel(),
    ) {
        val scrapUiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = true) {
            viewModel.errorFlow.collectLatest { onShowErrorSnackbar(it) }
        }

        when (val uiState = scrapUiState) {
            is ScrapUiState.Loading -> {}
            is ScrapUiState.Success -> {
                ScrapScreen(
                    onClickNews = onClickNews,
                    scrapNewsList = uiState.scraps,
                    deleteScrapNews = viewModel::deleteScrapNews
                )
            }

            is ScrapUiState.Fail -> {}
        }
    }
}
