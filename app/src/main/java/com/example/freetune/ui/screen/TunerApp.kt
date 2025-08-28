package com.example.freetune.ui.screen

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_DENIED
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.min
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.NoteInfo
import com.example.domain.resources.PitchResources.CENTS_DEVIATION
import com.example.domain.resources.TunerCanvasSettings.ANGLE_DEVIATION
import com.example.domain.resources.TunerCanvasSettings.ANIMATION_DURATION
import com.example.domain.resources.TunerCanvasSettings.CANVAS_ARC_CENTER_ANGLE
import com.example.freetune.R
import com.example.freetune.ui.components.TunerCanvas
import com.example.freetune.ui.components.TunerCircle
import com.example.freetune.ui.components.TunerInfo
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BaseApp(modifier: Modifier = Modifier) {
    val viewModel: TunerAppViewModel = hiltViewModel()
    val currentPitch = viewModel.pitch.collectAsState()
    val currentNote = viewModel.note.collectAsState()

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.getCurrentPitch()
        } else {
            viewModel.showPermissionDeniedMessage()
        }
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.RECORD_AUDIO
            ) == PERMISSION_DENIED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        } else {
            viewModel.getCurrentPitch()
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TunerArc(currentNote)
        TunerInfo(currentNote, currentPitch)
    }
}

@Composable
fun TunerArc(currentNote: State<NoteInfo>) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                dimensionResource(R.dimen.tuner_draw_scope)
            ),
        contentAlignment = Alignment.Center
    ) {

        val radius = with(LocalDensity.current) {
            min(maxWidth, maxWidth).toPx()
        }

        val animatedAngle = remember { Animatable(CANVAS_ARC_CENTER_ANGLE) }

        LaunchedEffect(currentNote.value.deviationInCents) {
            val targetAngle = CANVAS_ARC_CENTER_ANGLE - (currentNote.value.deviationInCents / CENTS_DEVIATION * ANGLE_DEVIATION)
            animatedAngle.animateTo(
                targetAngle,
                animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearOutSlowInEasing)
            )
        }

        val angleRad = Math.toRadians(animatedAngle.value.toDouble())
        val offsetX = (cos(angleRad) * radius).toFloat()
        val offsetY = (sin(angleRad) * radius).toFloat() + radius

        TunerCircle(modifier = Modifier)

        TunerCircle(
            modifier = Modifier.graphicsLayer {
                translationX = offsetX
                translationY = offsetY
            },
            backgroundColor = MaterialTheme.colorScheme.onPrimaryContainer
        )

        TunerCanvas()
    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    BaseApp()
}
