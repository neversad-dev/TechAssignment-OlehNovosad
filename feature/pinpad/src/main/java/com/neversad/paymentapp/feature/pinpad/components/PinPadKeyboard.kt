package com.neversad.paymentapp.feature.pinpad.components

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neversad.paymentapp.core.ui.theme.PaymentAppTheme


@Composable
internal fun PinPadKeyboard(
    onDigitClick: (Int) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = WindowInsets.safeDrawing
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(windowInsets)
        ) {
            Row(
                modifier = Modifier.weight(1f),
            ) {
                for (i in 1..3) {
                    NumberButton(
                        number = i.toString(),
                        onClick = { onDigitClick(i) }
                    )
                }
            }

            Row(
                modifier = Modifier.weight(1f),
            ) {
                for (i in 4..6) {
                    NumberButton(
                        number = i.toString(),
                        onClick = { onDigitClick(i) }
                    )
                }
            }

            Row(
                modifier = Modifier.weight(1f),
            ) {
                for (i in 7..9) {
                    NumberButton(
                        number = i.toString(),
                        onClick = { onDigitClick(i) }
                    )
                }
            }

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(
                    modifier = Modifier.weight(1f)
                )

                NumberButton(
                    number = "0",
                    onClick = { onDigitClick(0) }
                )
                ActionButton(
                    text = "OK",
                    onClick = onSubmit
                )
            }
        }
    }
}

@Composable
private fun RowScope.NumberButton(
    number: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .weight(1f)
            .fillMaxHeight(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Text(
            text = number,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}


@Composable
private fun RowScope.ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.weight(1f),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .sizeIn(minHeight = 64.dp)
                .padding(horizontal = 12.dp),
            shape = MaterialTheme.shapes.small,
        ) {
            val style = MaterialTheme.typography.headlineLarge
            BasicText(
                text = text,
                style = style.merge(
                    color = style.color.takeOrElse { LocalContentColor.current }
                ),
                autoSize = TextAutoSize.StepBased(),
                maxLines = 1,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PinPadKeyboardPreview() {
    PaymentAppTheme {
        PinPadKeyboard(
            onDigitClick = {},
            onSubmit = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}