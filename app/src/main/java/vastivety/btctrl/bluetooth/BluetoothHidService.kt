package vastivety.btctrl.bluetooth

import android.bluetooth.*
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import vastivety.btctrl.Constants

class BluetoothHidService(
    private val adapter: BluetoothAdapter,
    deviceName: String,
    deviceDesc: String,
    providerName: String
) {

    companion object {
        const val TAG = "vastivety.btctrl.bluetooth.BluetoothHidService"
    }

    private var hidDevice: BluetoothHidDevice? = null
    private val sdpSettings: BluetoothHidDeviceAppSdpSettings
    private var callback: Callback? = null

    private val profileListener: BluetoothProfile.ServiceListener

    private val deviceListener = object : BluetoothHidDevice.Callback() {
        override fun onAppStatusChanged(pluggedDevice: BluetoothDevice?, registered: Boolean) {
            Log.d(TAG, "onAppStatusChanged(pluggedDevice=$pluggedDevice, registered=$registered)")
            if (registered)
                callback?.onRegistered()
            else
                callback?.onUnregistered()
        }

        override fun onConnectionStateChanged(device: BluetoothDevice?, state: Int) {
            Log.d(TAG, "onConnectionStateChanged(device=$device, state=$state)")
            callback?.onConnectionStateChanged(device, state)
        }

        // TODO: find out what all these other callbacks are for

        override fun onGetReport(device: BluetoothDevice?, type: Byte, id: Byte, bufferSize: Int) {
            Log.d(TAG, "onGetReport(device=$device, type=$type, id=$id, bufferSize=$bufferSize)")
        }

        override fun onSetReport(device: BluetoothDevice?, type: Byte, id: Byte, data: ByteArray?) {
            Log.d(TAG, "onSetReport(device=$device, type=$type, id=$id, data=$data)")
        }

        override fun onSetProtocol(device: BluetoothDevice?, protocol: Byte) {
            Log.d(TAG, "onSetProtocol(device=$device, protocol=$protocol)")
        }

        override fun onInterruptData(device: BluetoothDevice?, reportId: Byte, data: ByteArray?) {
            Log.d(TAG, "onInterruptData(device=$device, reportId=$reportId, data=$data)")
        }

        override fun onVirtualCableUnplug(device: BluetoothDevice?) {
            Log.d(TAG, "onVirtualCableUnplug(device=$device)")
        }
    }

    init {
        sdpSettings = BluetoothHidDeviceAppSdpSettings(
            deviceName,
            deviceDesc,
            providerName,
            BluetoothHidDevice.SUBCLASS1_COMBO,
            Constants.HID_DEVICE_DESCRIPTORS
        )

        profileListener = object : BluetoothProfile.ServiceListener {
            @Throws(SecurityException::class)
            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
                if (profile == BluetoothProfile.HID_DEVICE && proxy != null) {
                    Log.d(TAG, "Service connected")
                    hidDevice = proxy as BluetoothHidDevice
                    val success = hidDevice?.registerApp(
                        sdpSettings,
                        null, // TODO: provide QOS settings(?)
                        null,
                        Runnable::run,
                        deviceListener
                    )
                    Log.d(TAG, "hidDevice?.registerApp=$success")
                    // TODO: report success value
                    /*
                    Note: Return value indicates if command
                    has been sent successfully. If unsuccessful,
                    restart of Bluetooth has proven to be effective.
                    This could be hinted as a possible fix to the user.
                     */
                }
            }

            @Throws(SecurityException::class)
            override fun onServiceDisconnected(profile: Int) {
                Log.d(TAG, "Service disconnected")
                hidDevice?.unregisterApp()
            }
        }
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
    fun register(context: Context): Boolean {
        return adapter.getProfileProxy(context, profileListener, BluetoothProfile.HID_DEVICE)
    }

    @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
    fun unregister() {
        hidDevice?.unregisterApp()
        adapter.closeProfileProxy(BluetoothProfile.HID_DEVICE, hidDevice)
    }

    @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
    fun connect(device: BluetoothDevice) {
        hidDevice?.connect(device)
    }

    @RequiresPermission("android.permission.BLUETOOTH_CONNECT")
    fun disconnect(device: BluetoothDevice) {
        hidDevice?.disconnect(device)
    }

    interface Callback {

        fun onRegistered()

        fun onUnregistered()

        fun onConnectionStateChanged(device: BluetoothDevice?, state: Int)
    }
}
