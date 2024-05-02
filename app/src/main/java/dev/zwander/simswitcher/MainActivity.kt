package dev.zwander.simswitcher

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SubscriptionManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dev.zwander.simswitcher.components.SwitcherItem
import dev.zwander.simswitcher.data.SwitcherItemData
import dev.zwander.simswitcher.data.SwitcherType
import dev.zwander.simswitcher.ui.theme.SIMSwitcherTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.lsposed.hiddenapibypass.HiddenApiBypass

class MainActivity : ComponentActivity(), CoroutineScope by MainScope() {
    private val subsManager by lazy { getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager }

    private val permissionRequester = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (!result) {
            finish()
        } else {
            init()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HiddenApiBypass.setHiddenApiExemptions("")
        }

        if (checkCallingOrSelfPermission(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            init()
        } else {
            permissionRequester.launch(android.Manifest.permission.READ_PHONE_STATE)
        }
    }

    @SuppressLint("MissingPermission")
    private fun init() {
        setContent {
            var currentDataSub by remember {
                mutableIntStateOf(SubscriptionManager.getDefaultDataSubscriptionId())
            }
            var currentVoiceSub by remember {
                mutableIntStateOf(SubscriptionManager.getDefaultVoiceSubscriptionId())
            }
            var currentSmsSub by remember {
                mutableIntStateOf(SubscriptionManager.getDefaultSmsSubscriptionId())
            }

            val items = remember {
                listOf(
                    SwitcherItemData(
                        type = SwitcherType.DATA,
                        labelRes = R.string.default_data,
                        actionRes = R.string.switch_data,
                        currentValue = { subsManager.getActiveSubscriptionInfo(currentDataSub) },
                    ),
                    SwitcherItemData(
                        type = SwitcherType.VOICE,
                        labelRes = R.string.default_voice,
                        actionRes = R.string.switch_voice,
                        currentValue = { subsManager.getActiveSubscriptionInfo(currentVoiceSub) },
                    ),
                    SwitcherItemData(
                        type = SwitcherType.SMS,
                        labelRes = R.string.default_sms,
                        actionRes = R.string.switch_sms,
                        currentValue = { subsManager.getActiveSubscriptionInfo(currentSmsSub) },
                    ),
                )
            }

            DisposableEffect(null) {
                val subsListener = object : SubscriptionManager.OnSubscriptionsChangedListener() {
                    override fun onSubscriptionsChanged() {
                        currentDataSub = SubscriptionManager.getDefaultDataSubscriptionId()
                        currentVoiceSub = SubscriptionManager.getDefaultVoiceSubscriptionId()
                        currentSmsSub = SubscriptionManager.getDefaultSmsSubscriptionId()
                    }
                }

                val lifecycleObserver = object : DefaultLifecycleObserver {
                    override fun onResume(owner: LifecycleOwner) {
                        subsListener.onSubscriptionsChanged()
                    }
                }

                lifecycle.addObserver(lifecycleObserver)
                @Suppress("DEPRECATION")
                subsManager.addOnSubscriptionsChangedListener(subsListener)

                onDispose {
                    lifecycle.removeObserver(lifecycleObserver)
                    subsManager.removeOnSubscriptionsChangedListener(subsListener)
                }
            }

            SIMSwitcherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding()
                            .systemBarsPadding(),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(items = items, key = { it.type }) { item ->
                            SwitcherItem(
                                data = item,
                                modifier = Modifier.padding(8.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
