package vastivety.btctrl

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import vastivety.btctrl.bluetooth.BluetoothManager
import vastivety.btctrl.databinding.ActivityMainBinding
import vastivety.btctrl.ui.DeviceListAdapter

@SuppressLint("MissingPermission") // TODO: permissions
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "vastivety.btctrl.MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var manager: BluetoothManager
    private val bondedDevices = mutableSetOf<BluetoothDevice>()

    private val bondedDeviceClickedListener = object : DeviceListAdapter.ItemClickedListener {
        override fun onItemClicked(position: Int) {
            startControlActivity(bondedDevices.elementAt(position))
        }
    }

    private fun startControlActivity(device: BluetoothDevice) {
        val intent = Intent(this, ControlActivity::class.java).apply {
            putExtra(Constants.EXTRA_BLUETOOTH_DEVICE, device)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.deviceList.layoutManager = LinearLayoutManager(this)
        binding.deviceList.adapter = DeviceListAdapter(bondedDevices, bondedDeviceClickedListener)

        manager = BluetoothManager(this, lifecycle)
    }

    override fun onResume() {
        super.onResume()
        updateDevices()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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

    private fun updateDevices() {
        // TODO: compute diff between bondedDevices and manager.adapter.bondedDevices
        val removedCount = bondedDevices.size
        bondedDevices.clear()
        binding.deviceList.adapter?.notifyItemRangeRemoved(0, removedCount)
        bondedDevices.addAll(manager.adapter.bondedDevices)
        binding.deviceList.adapter?.notifyItemRangeInserted(0, bondedDevices.size)
    }
}
