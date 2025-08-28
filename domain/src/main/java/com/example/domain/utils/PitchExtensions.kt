package com.example.domain.utils

import com.example.domain.model.CorrectionType
import com.example.domain.model.NoteInfo
import com.example.domain.resources.PitchResources.BASE_A_MIDI_NUMBER
import com.example.domain.resources.PitchResources.BASE_A_NOTE
import com.example.domain.resources.PitchResources.NOTES_IN_OCTAVE
import com.example.domain.resources.PitchResources.NOTES_LIST
import com.example.domain.resources.PitchResources.PERM_ERROR
import kotlin.math.abs
import kotlin.math.log2
import kotlin.math.roundToInt

fun Float.toNoteInfo(): NoteInfo {
    val midiNumber = NOTES_IN_OCTAVE * log2(this / BASE_A_NOTE) + BASE_A_MIDI_NUMBER
    val roundedMidiNumber = midiNumber.roundToInt()
    val noteName = "${NOTES_LIST[roundedMidiNumber % NOTES_IN_OCTAVE]}${roundedMidiNumber / NOTES_IN_OCTAVE - 1}"
    val deviationInCents = ((roundedMidiNumber - midiNumber) * 100).roundToInt()
    val correction = if (abs(deviationInCents) > PERM_ERROR) CorrectionType.OutOfTune else CorrectionType.NormalPitch
    
    return NoteInfo(noteName, deviationInCents, correction)
}