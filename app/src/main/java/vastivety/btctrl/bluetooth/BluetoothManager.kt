package vastivety.btctrl.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class BluetoothManager(
    private val context: Context,
    lifecycle: Lifecycle
) : DefaultLifecycleObserver {

    companion object {
        const val TAG = "vastivety.btctrl.bluetooth.BluetoothManager"
    }

    val adapter: BluetoothAdapter

    private val stateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (BluetoothAdapter.ACTION_STATE_CHANGED == intent?.action)
                onStateChanged()
        }
    }

    private val scanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> Log.d(TAG, "onReceive: ACTION_DISCOVERY_STARTED")
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> Log.d(TAG, "onReceive: ACTION_DISCOVERY_FINISHED")
                BluetoothDevice.ACTION_FOUND -> Log.d(TAG, "onReceive: ACTION_FOUND")
                BluetoothDevice.ACTION_NAME_CHANGED -> Log.d(TAG, "onReceive: ACTION_NAME_CHANGED")
                BluetoothDevice.ACTION_BOND_STATE_CHANGED -> Log.d(TAG, "onReceive: ACTION_BOND_STATE_CHANGED")
                else -> return
            }
        }
    }

    init {
        val manager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        adapter = manager.adapter
        lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        Log.d(TAG, "onCreate")
        registerStateReceiver()
    }

    override fun onResume(owner: LifecycleOwner) {
        onStateChanged()
    }

    override fun onPause(owner: LifecycleOwner) {
        unregisterScanReceiver()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        unregisterStateReceiver()
    }

    private fun onStateChanged() {
        when (adapter.state) {
            BluetoothAdapter.STATE_ON -> {
                registerScanReceiver()
            }
            BluetoothAdapter.STATE_TURNING_OFF -> {
                unregisterScanReceiver()
            }
            else -> Unit
        }
    }

    private fun registerStateReceiver() {
        context.registerReceiver(stateReceiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))
    }

    private fun unregisterStateReceiver() {
        context.unregisterReceiver(stateReceiver)
    }

    private fun registerScanReceiver() {
        val filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothDevice.ACTION_NAME_CHANGED)
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        context.registerReceiver(scanReceiver, filter)
    }

    private fun unregisterScanReceiver() {
        context.unregisterReceiver(scanReceiver)
    }
}
