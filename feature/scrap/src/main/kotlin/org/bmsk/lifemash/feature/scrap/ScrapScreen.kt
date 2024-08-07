package org.bmsk.lifemash.feature.scrap

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.bmsk.lifemash.core.designsystem.theme.LifeMashTheme
import org.bmsk.lifemash.core.model.DateParser
import org.bmsk.lifemash.core.model.NewsModel
import org.bmsk.lifemash.feature.scrap.component.ScrapNewsItem
import org.bmsk.lifemash.feature.scrap.component.rememberScrapNewsItemState

@Composable
internal fun ScrapScreen(
    onClickNews: (url: String) -> Unit,
    scrapNewsList: PersistentList<NewsModel>,
    deleteScrapNews: (NewsModel) -> Unit,
) {
    val scrapNewsItemState = rememberScrapNewsItemState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(scrapNewsList) { news ->
            ScrapNewsItem(
                newsModel = news,
                state = scrapNewsItemState,
                onClick = { onClickNews(news.link) },
                onClickDelete = { deleteScrapNews(news) },
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ScrapScreenPreview() {
    LifeMashTheme {
        ScrapScreen(
            onClickNews = {},
            scrapNewsList = persistentListOf(
                NewsModel(
                    title = "NewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsN",
                    link = "",
                    pubDate = DateParser.parseDate("Tue, 20 Jun 2023 02:57:43 GMT"),
                    imageUrl = "",
                ),
                NewsModel(
                    title = "NewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsN",
                    link = "",
                    pubDate = DateParser.parseDate("Tue, 20 Jun 2023 02:57:43 GMT"),
                    imageUrl = "",
                ),
                NewsModel(
                    title = "NewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsN",
                    link = "",
                    pubDate = DateParser.parseDate("Tue, 20 Jun 2023 02:57:43 GMT"),
                    imageUrl = "",
                ),
                NewsModel(
                    title = "NewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNe",
                    link = "",
                    pubDate = DateParser.parseDate("Tue, 20 Jun 2023 02:57:43 GMT"),
                    imageUrl = "",
                ),
                NewsModel(
                    title = "NewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsN",
                    link = "",
                    pubDate = DateParser.parseDate("Tue, 20 Jun 2023 02:57:43 GMT"),
                    imageUrl = "",
                ),
                NewsModel(
                    title = "NewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsN",
                    link = "",
                    pubDate = DateParser.parseDate("Tue, 20 Jun 2023 02:57:43 GMT"),
                    imageUrl = "",
                ),
                NewsModel(
                    title = "NewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNew" +
                            "sNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNewsNews",
                    link = "",
                    pubDate = DateParser.parseDate("Tue, 20 Jun 2023 02:57:43 GMT"),
                    imageUrl = "",
                ),
            ),
            deleteScrapNews = {},
        )
    }
}
