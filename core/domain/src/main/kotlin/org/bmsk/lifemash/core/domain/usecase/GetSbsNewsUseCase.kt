package org.bmsk.lifemash.core.domain.usecase

import org.bmsk.lifemash.core.domain.repository.NewsRepository
import org.bmsk.lifemash.core.model.NewsModel
import org.bmsk.lifemash.core.model.section.SbsSection
import javax.inject.Inject

class GetSbsNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
) {
    suspend operator fun invoke(section: SbsSection): List<NewsModel> {
        return newsRepository.getSbsNews2(section)
    }
}
