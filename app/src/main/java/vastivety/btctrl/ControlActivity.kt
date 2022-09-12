package vastivety.btctrl

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import vastivety.btctrl.UtilFunctions.getParcelableExtraPolyfill
import vastivety.btctrl.bluetooth.BluetoothHidService
import vastivety.btctrl.bluetooth.BluetoothManager
import vastivety.btctrl.databinding.ActivityControlBinding

@SuppressLint("MissingPermission") // TODO: permissions
class ControlActivity : AppCompatActivity(), BluetoothHidService.Callback {

    companion object {
        const val TAG = "vastivety.btctrl.ControlActivity"
    }

    private lateinit var binding: ActivityControlBinding
    private lateinit var device: BluetoothDevice
    private lateinit var manager: BluetoothManager
    private lateinit var service: BluetoothHidService
    private var hideProgressIndicator = true

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityControlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        // pause app when navigating back instead of going back to MainActivity
        onBackPressedDispatcher.addCallback(this) {
            moveTaskToBack(true)
        }

        // get BluetoothDevice from intent that started this activity
        val maybeDevice = intent.getParcelableExtraPolyfill(Constants.EXTRA_BLUETOOTH_DEVICE, BluetoothDevice::class.java)
        if (maybeDevice == null) {
            finish()
            return
        }
        device = maybeDevice
        Log.d(TAG, "Started with device ${device.alias ?: device.name}")
        title = device.alias ?: device.name

        // initialize helper class
        manager = BluetoothManager(this, lifecycle)

        // initialize HIDDevice profile helper class
        service = BluetoothHidService(
            manager.adapter,
            manager.adapter.name,
            getString(R.string.service_description),
            getString(R.string.app_name)
        )
        service.setCallback(this)
    }

    override fun onResume() {
        super.onResume()
        showIndeterminateProgressIndicator()
        val success = service.register(this)
        Log.d(TAG, "Service request success: $success")
        // TODO: check what happens if HIDDevice profile is not available
    }

    override fun onPause() {
        super.onPause()
        service.unregister()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_control, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_toggle_keyboard -> {
                showIndeterminateProgressIndicator()
                true
            }
            R.id.action_toggle_fullscreen -> {
                fillAndHideProgressIndicator()
                true
            }
            R.id.action_mode -> {
                true
            }
            R.id.action_disconnect -> {
                service.disconnect(device)
                finish()
                true
            }
            R.id.action_settings -> {
                true
            }
            R.id.action_help -> {
                true
            }
            R.id.action_about -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRegistered() {
        Log.d(TAG, "HID service registered")
        Log.d(TAG, "Attempting to connect")
        service.connect(device)
    }

    override fun onUnregistered() {
        Log.d(TAG, "HID service unregistered")
    }

    override fun onConnectionStateChanged(device: BluetoothDevice?, state: Int) {
        when (state) {
            BluetoothProfile.STATE_CONNECTED -> {
                Log.d(TAG, "Connected")
                fillAndHideProgressIndicator()
            }
            BluetoothProfile.STATE_DISCONNECTING -> {
                Log.d(TAG, "Disconnecting")
            }
            BluetoothProfile.STATE_DISCONNECTED -> {
                Log.d(TAG, "Disconnected")
            }
            BluetoothProfile.STATE_CONNECTING -> {
                Log.d(TAG, "Connecting")
            }
        }
    }

    private fun fillAndHideProgressIndicator() {
        hideProgressIndicator = true
        runOnUiThread {
            binding.progressIndicator.setProgressCompat(100, true)
        }
        Handler(mainLooper).postDelayed({
            if (hideProgressIndicator) {
                binding.progressIndicator.setVisibilityAfterHide(View.GONE)
                binding.progressIndicator.hide()
            }
        }, 4000)
    }

    private fun showIndeterminateProgressIndicator() {
        hideProgressIndicator = false
        runOnUiThread {
            binding.progressIndicator.progress = 50
            binding.progressIndicator.isIndeterminate = true
            binding.progressIndicator.show()
        }
    }
}
