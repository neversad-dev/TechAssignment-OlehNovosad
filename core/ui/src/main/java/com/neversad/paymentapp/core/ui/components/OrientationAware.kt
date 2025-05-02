package com.neversad.paymentapp.core.ui.components

import android.content.res.Configuration
import android.preference.PreferenceActivity.Header
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

interface OrientationAwareScope {
    fun Modifier.weight(weight: Float, fill: Boolean = true): Modifier

    @Composable
    fun Modifier.headerInsets(): Modifier

    @Composable
    fun Modifier.footerInsets(): Modifier

}

private class RowAwareScope(private val row: RowScope) : OrientationAwareScope {
    @Stable
    override fun Modifier.weight(w: Float, fill: Boolean): Modifier {
        require(w > 0.0) { "invalid weight; must be greater than zero" }
        return this.then(
            with(row) {
                Modifier.weight(w, fill)
            }
        )
    }

    @Composable
    override fun Modifier.headerInsets() =
        windowInsetsPadding(
            WindowInsets.safeDrawing.only(
                WindowInsetsSides.Start + WindowInsetsSides.Vertical
            )
        )

    @Composable
    override fun Modifier.footerInsets() =
        windowInsetsPadding(
            WindowInsets.safeDrawing.only(
                WindowInsetsSides.End + WindowInsetsSides.Vertical
            )
        )
}

private class ColumnAwareScope(private val column: ColumnScope) : OrientationAwareScope {
    @Stable
    override fun Modifier.weight(w: Float, fill: Boolean): Modifier {
        require(w > 0.0) { "invalid weight; must be greater than zero" }
        return this.then(
            with(column) {
                Modifier.weight(w, fill)
            }
        )
    }

    @Composable
    override fun Modifier.headerInsets() =
        windowInsetsPadding(
            WindowInsets.safeDrawing.only(
                WindowInsetsSides.Top + WindowInsetsSides.Horizontal
            )
        )

    @Composable
    override fun Modifier.footerInsets() =
        windowInsetsPadding(
            WindowInsets.safeDrawing.only(
                WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal
            )
        )
}

@Composable
fun OrientationAware(
    modifier: Modifier = Modifier,
    content: @Composable OrientationAwareScope.() -> Unit
) {
    val orientation = LocalConfiguration.current.orientation
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(modifier = modifier.fillMaxSize()) {
            RowAwareScope(this).content()
        }
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            ColumnAwareScope(this).content()
        }
    }
}