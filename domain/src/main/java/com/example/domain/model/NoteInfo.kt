package com.example.domain.model

data class NoteInfo(
    val name: String = "",
    val deviationInCents: Int = 0,
    val correction: CorrectionType = CorrectionType.NormalPitch
)

sealed interface CorrectionType {
    data object OutOfTune: CorrectionType
    data object NormalPitch: CorrectionType
}
