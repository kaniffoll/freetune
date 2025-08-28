package com.example.freetune.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.example.freetune.R

@Composable
fun TunerCircle(
    modifier: Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    borderColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Box(
        modifier = modifier
            .size(dimensionResource(R.dimen.note_circle_size))
            .clip(shape = CircleShape)
            .border(
                dimensionResource(R.dimen.border_width),
                color = borderColor,
                shape = CircleShape
            )
            .background(backgroundColor)
    )
}