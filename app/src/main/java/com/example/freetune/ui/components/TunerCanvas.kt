package com.example.freetune.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.zIndex
import com.example.domain.resources.TunerCanvasSettings.CANVAS_ARC_SCALE
import com.example.domain.resources.TunerCanvasSettings.CANVAS_ARC_START_ANGLE
import com.example.domain.resources.TunerCanvasSettings.CANVAS_ARC_SWEEP_ANGLE
import com.example.freetune.R

@Composable
fun TunerCanvas() {
    val arcColor = MaterialTheme.colorScheme.onPrimaryContainer
    val strokeWidth = dimensionResource(R.dimen.border_width)
    Canvas(
        Modifier
            .fillMaxWidth()
            .height(
                dimensionResource(R.dimen.tuner_draw_scope)
            )
            .zIndex(-1F)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 2
        scale(CANVAS_ARC_SCALE, CANVAS_ARC_SCALE) {
            drawArc(
                color = arcColor,
                startAngle = CANVAS_ARC_START_ANGLE,
                sweepAngle = CANVAS_ARC_SWEEP_ANGLE,
                useCenter = false,
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(centerX - radius, centerY),
                style = Stroke(
                    width = strokeWidth.toPx(), cap = StrokeCap.Round
                )
            )
        }
    }
}