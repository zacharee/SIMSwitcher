package dev.zwander.simswitcher.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun SwitcherItemCard(
    image: ImageBitmap,
    text: String,
    modifier: Modifier = Modifier,
    tint: Color? = null,
    onClick: (() -> Unit)? = null,
) {
    val colors = CardDefaults.cardColors()
    val border = ButtonDefaults.outlinedButtonBorder

    @Composable
    fun ColumnScope.contents() {
        CardContents(
            image = image,
            text = text,
            tint = tint,
            modifier = Modifier.padding(12.dp)
                .align(Alignment.CenterHorizontally),
        )
    }

    if (onClick != null) {
        OutlinedCard(
            colors = colors,
            border = border,
            modifier = modifier,
            onClick = onClick,
        ) {
            contents()
        }
    } else {
        OutlinedCard(
            colors = colors,
            border = border,
            modifier = modifier,
        ) {
            contents()
        }
    }
}

@Composable
private fun CardContents(
    image: ImageBitmap,
    text: String,
    tint: Color?,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Image(
            bitmap = image,
            contentDescription = null,
            colorFilter = tint?.let { ColorFilter.tint(tint) },
            modifier = Modifier.size(24.dp),
        )

        Text(
            text = text,
        )
    }
}
