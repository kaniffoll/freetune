package com.example.freetune.ui.screen

import androidx.lifecycle.ViewModel
import com.example.domain.audio.analyzer.AudioAnalyzer
import com.example.domain.model.NoteInfo
import com.example.domain.resources.StringResources
import com.example.domain.utils.toNoteInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TunerAppViewModel @Inject constructor(
    private val audioAnalyzer: AudioAnalyzer
) : ViewModel() {

    private var _pitch = MutableStateFlow("")
    val pitch = _pitch.asStateFlow()

    private var _note = MutableStateFlow(NoteInfo())
    val note = _note.asStateFlow()

    fun getCurrentPitch() {
        audioAnalyzer.startListening { pitch ->
            _pitch.value = String.Companion.format(Locale.US, "%.3f", pitch)
            _note.value = pitch.toNoteInfo()
        }
    }

    fun showPermissionDeniedMessage() {
        _pitch.value = StringResources.GET_PERMISSION_ERROR
    }
}