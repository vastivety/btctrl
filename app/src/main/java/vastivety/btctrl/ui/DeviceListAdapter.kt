package vastivety.btctrl.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vastivety.btctrl.R

class DeviceListAdapter(
    private val deviceSet: Set<BluetoothDevice>,
    private val itemClickedListener: ItemClickedListener) :
    RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false),
            itemClickedListener
        )
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = deviceSet.elementAt(position)
        val bluetoothClass = device.bluetoothClass
        holder.setIndex(position)
        holder.setIcon(getIcon(bluetoothClass.majorDeviceClass, bluetoothClass.deviceClass))
        holder.setName(device.alias ?: device.name)
    }

    override fun getItemCount(): Int = deviceSet.size

    private fun getIcon(bluetoothMajorDeviceClass: Int, blutoothDeviceClass: Int): Int {
        return when (bluetoothMajorDeviceClass) {
            BluetoothClass.Device.Major.AUDIO_VIDEO -> {
                when (blutoothDeviceClass) {
                    BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER -> R.drawable.ic_videocam
                    BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO -> R.drawable.ic_headset
                    BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE -> R.drawable.ic_headset
                    BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES -> R.drawable.ic_headphones
                    BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO -> R.drawable.ic_speaker
                    BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER -> R.drawable.ic_speaker
                    BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE -> R.drawable.ic_mic
                    BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO -> R.drawable.ic_headphones
                    BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA -> R.drawable.ic_videocam
                    BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING -> R.drawable.ic_videocam // TODO: icon?
                    BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER -> R.drawable.ic_videocam // TODO: icon
                    BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY -> R.drawable.ic_smart_toy
                    BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR -> R.drawable.ic_videocam // TODO: icon
                    BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET -> R.drawable.ic_headphones
                    else -> R.drawable.ic_bluetooth
                }
            }
            BluetoothClass.Device.Major.COMPUTER -> R.drawable.ic_computer
            BluetoothClass.Device.Major.HEALTH -> R.drawable.ic_monitor_heart
            BluetoothClass.Device.Major.IMAGING -> R.drawable.ic_videocam
            BluetoothClass.Device.Major.PERIPHERAL -> {
                when (blutoothDeviceClass) {
                    BluetoothClass.Device.PERIPHERAL_KEYBOARD -> R.drawable.ic_keyboard
                    BluetoothClass.Device.PERIPHERAL_POINTING -> R.drawable.ic_mouse
                    BluetoothClass.Device.PERIPHERAL_KEYBOARD_POINTING -> R.drawable.ic_keyboard // TODO: create separate icon?
                    else -> R.drawable.ic_bluetooth
                }
            }
            BluetoothClass.Device.Major.PHONE -> R.drawable.ic_smartphone
            BluetoothClass.Device.Major.TOY -> R.drawable.ic_smart_toy
            BluetoothClass.Device.Major.WEARABLE -> R.drawable.ic_watch
            else -> R.drawable.ic_bluetooth
        }
    }

    interface ItemClickedListener {
        fun onItemClicked(position: Int)
    }

    class ViewHolder(itemView: View, listener: ItemClickedListener) :
        RecyclerView.ViewHolder(itemView) {

        private var index: Int = -1
        private val ivIcon: ImageView
        private val tvName: TextView
        private val tvInfo: TextView

        init {
            itemView.isClickable = true
            ivIcon = itemView.findViewById(R.id.device_icon)
            tvName = itemView.findViewById(R.id.device_name)
            tvInfo = itemView.findViewById(R.id.device_info)
            itemView.setOnClickListener {
                listener.onItemClicked(index)
            }
        }

        fun setIndex(index: Int) {
            this.index = index
        }

        fun setIcon(drawableResId: Int) {
            ivIcon.setImageResource(drawableResId)
        }

        fun setName(name: String) {
            tvName.text = name
        }

        fun setInfo(stringResId: Int) {
            tvInfo.setText(stringResId)
        }
    }
}
