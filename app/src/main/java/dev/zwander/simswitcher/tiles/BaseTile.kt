package dev.zwander.simswitcher.tiles

import android.graphics.drawable.Icon
import android.service.quicksettings.TileService
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.service.quicksettings.PendingIntentActivityWrapper
import androidx.core.service.quicksettings.TileServiceCompat
import dev.zwander.simswitcher.R
import dev.zwander.simswitcher.data.SwitcherType
import dev.zwander.simswitcher.util.createSwitcherIntent

class DataTile : BaseTile(
    type = SwitcherType.DATA,
    iconRes = R.drawable.baseline_data_usage_24,
    labelRes = R.string.switch_data,
)

class VoiceTile : BaseTile(
    type = SwitcherType.VOICE,
    iconRes = R.drawable.baseline_call_24,
    labelRes = R.string.switch_voice,
)

class SMSTile : BaseTile(
    type = SwitcherType.SMS,
    iconRes = R.drawable.baseline_sms_24,
    labelRes = R.string.switch_sms,
)

sealed class BaseTile(
    val type: SwitcherType,
    @DrawableRes
    val iconRes: Int,
    @StringRes
    val labelRes: Int,
) : TileService() {
    override fun onStartListening() {
        super.onStartListening()

        qsTile?.icon = Icon.createWithResource(resources, iconRes)
        qsTile?.label = resources.getString(labelRes)
        qsTile?.updateTile()
    }

    override fun onClick() {
        TileServiceCompat.startActivityAndCollapse(
            this,
            PendingIntentActivityWrapper(
                this,
                100,
                createSwitcherIntent(type, true),
                0,
                false,
            )
        )
    }
}
