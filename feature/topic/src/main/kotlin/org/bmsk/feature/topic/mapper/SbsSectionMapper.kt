package org.bmsk.feature.topic.mapper

import org.bmsk.core.model.section.SbsSection
import org.bmsk.feature.topic.SectionChip

internal fun SbsSection.asChip(): SectionChip = when (this) {
    SbsSection.ECONOMICS -> SectionChip.ECONOMY
    SbsSection.SOCIAL -> SectionChip.SOCIAL
    SbsSection.ENTERTAINMENT_BROADCAST -> SectionChip.ENTERTAINMENT_BROADCAST
    SbsSection.LIFE_CULTURE -> SectionChip.LIFE_CULTURE
    SbsSection.POLITICS -> SectionChip.POLITICS
    SbsSection.INTERNATIONAL_GLOBAL -> SectionChip.INTERNATIONAL_GLOBAL
    SbsSection.SPORT -> SectionChip.SPORT
}
