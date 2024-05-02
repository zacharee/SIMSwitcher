package dev.zwander.simswitcher.data

import android.telephony.SubscriptionInfo
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

data class SwitcherItemData(
    val type: SwitcherType,
    @StringRes
    val labelRes: Int,
    @StringRes
    val actionRes: Int,
    val currentValue: @Composable () -> SubscriptionInfo,
)
