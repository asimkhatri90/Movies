package com.asimkhatri.movies.ui.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun RatingView(rating: Float) {
    var progressColor = Color(0xff23d07a)
    var progressTrackColor = Color(0x6623d07a)
    if (rating < 0.7) {
        progressColor = Color(0xffd2d530)
        progressTrackColor = Color(0x66d2d530)
    }
    val ratingText = (rating * 100).roundToInt()
    val backgroundColor = Color(0xff081c22)
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .size(62.dp)
                .clip(CircleShape)
                .background(backgroundColor),
    ) {
        CustomCircularProgressIndicator(
            size = 56.dp,
            strokeWidth = 6.dp,
            progress = rating,
            progressColor = progressColor,
            progressTrackColor = progressTrackColor,
        )
        Text(text = "$ratingText%", color = Color.White)
    }
}

@Composable
fun CustomCircularProgressIndicator(
    size: Dp = 96.dp,
    strokeWidth: Dp = 12.dp,
    progress: Float,
    progressColor: Color,
    progressTrackColor: Color,
) {
    Canvas(modifier = Modifier.size(size = size)) {
        val gradientBrush =
            Brush.radialGradient(colors = listOf(progressColor, progressColor))

        val strokeWidthPx = strokeWidth.toPx()
        val arcSize = size.toPx() - strokeWidthPx

        // Background Arc Implementation
        drawArc(
            color = progressTrackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
            size = Size(arcSize, arcSize),
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
        )
        withTransform({ rotate(degrees = 270f, pivot = center) }) {
            drawArc(
                brush = gradientBrush,
                startAngle = 0f,
                sweepAngle = progress * 360f,
                useCenter = false,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
            )
        }
    }
}
