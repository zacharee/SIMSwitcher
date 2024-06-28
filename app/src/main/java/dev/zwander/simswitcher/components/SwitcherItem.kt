package dev.zwander.simswitcher.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import dev.zwander.simswitcher.data.SwitcherItemData
import dev.zwander.simswitcher.util.launchSwitcher

@Composable
fun SwitcherItem(
    data: SwitcherItemData,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val subInfo = data.currentSubInfo()
    val iconSize = with (LocalDensity.current) { 24.dp.roundToPx() }

    val iconBitmap = remember(subInfo) {
        subInfo.createIconBitmap(context).asImageBitmap()
    }
    val actionBitmap = remember(subInfo) {
        ResourcesCompat.getDrawable(context.resources, data.iconRes, context.resources.newTheme())!!
            .toBitmap(
                width = iconSize,
                height = iconSize,
            )
            .asImageBitmap()
    }

    Card {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(id = data.labelRes),
                style = MaterialTheme.typography.titleLarge,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                SwitcherItemCard(
                    image = iconBitmap,
                    text = subInfo.displayName.toString(),
                    modifier = Modifier.weight(1f),
                )

                Box(
                    modifier = Modifier.width(16.dp).height(1.dp)
                        .background(MaterialTheme.colorScheme.outline),
                )

                SwitcherItemCard(
                    image = actionBitmap,
                    text = stringResource(data.actionRes),
                    modifier = Modifier.weight(1f),
                    tint = LocalContentColor.current,
                    onClick = { context.launchSwitcher(data.type) },
                )
            }
        }
    }
}
