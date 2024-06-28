package dev.zwander.simswitcher.data

import android.telephony.SubscriptionInfo
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.State

data class SwitcherItemData(
    val type: SwitcherType,
    @StringRes
    val labelRes: Int,
    @StringRes
    val actionRes: Int,
    @DrawableRes
    val iconRes: Int,
    val currentSubInfo: State<SubscriptionInfo>,
)
