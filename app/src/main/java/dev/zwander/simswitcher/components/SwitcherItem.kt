package dev.zwander.simswitcher.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.zwander.simswitcher.data.SwitcherItemData
import dev.zwander.simswitcher.util.launchSwitcher

@Composable
fun SwitcherItem(
    data: SwitcherItemData,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val subInfo = data.currentSubInfo()

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
            ) {
                OutlinedCard(
                    colors = CardDefaults.cardColors(),
                    border = ButtonDefaults.outlinedButtonBorder,
                    modifier = Modifier.weight(0.5f),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Image(
                            bitmap = subInfo.createIconBitmap(context).asImageBitmap(),
                            contentDescription = null,
                        )

                        Text(
                            text = subInfo.displayName.toString(),
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.weight(1f),
                )

                OutlinedButton(
                    onClick = { context.launchSwitcher(data.type) },
                ) {
                    Text(
                        text = stringResource(id = data.actionRes),
                    )
                }
            }
        }
    }
}
