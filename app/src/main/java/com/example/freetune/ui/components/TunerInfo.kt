package com.example.freetune.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.domain.model.CorrectionType
import com.example.domain.model.NoteInfo
import com.example.freetune.R

@Composable
fun TunerInfo(currentNote: State<NoteInfo>, currentPitch: State<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.tuner_info_padding)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentNote.value.name,
            color = when (currentNote.value.correction) {
                is CorrectionType.OutOfTune -> MaterialTheme.colorScheme.error
                is CorrectionType.NormalPitch -> MaterialTheme.colorScheme.primary
            },
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "${currentPitch.value} ${stringResource(R.string.HZ)}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}