package dev.zwander.simswitcher.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import dev.zwander.simswitcher.data.SwitcherType

fun Context.launchSwitcher(type: SwitcherType, newTask: Boolean = false) {
    startActivity(createSwitcherIntent(type, newTask))
}

fun createSwitcherIntent(type: SwitcherType, newTask: Boolean = false): Intent {
    val switcherIntent = Intent(Intent.ACTION_MAIN)
    switcherIntent.`package` = "com.android.settings"
    switcherIntent.component = ComponentName("com.android.settings", "com.android.settings.sim.SimDialogActivity")
    switcherIntent.putExtra("dialog_type", type.value)

    if (newTask) {
        switcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    return switcherIntent
}
