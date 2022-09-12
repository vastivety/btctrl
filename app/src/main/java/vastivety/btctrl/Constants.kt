package vastivety.btctrl

import android.os.ParcelUuid
import vastivety.btctrl.UtilFunctions.byteArray

object Constants {

    const val EXTRA_BLUETOOTH_DEVICE = "vastivety.btctrl.extra.BLUETOOTH_DEVICE"

    /*
    The following values and comments were adapted from
    ginkage/wearmouse/app/src/main/java/com/ginkage/wearmouse/bluetooth/Constants.java
    licensed under the Apache License 2.0 (http://www.apache.org/licenses/LICENSE-2.0).
     */
    // https://www.usb.org/sites/default/files/hid1_11.pdf
    val HID_DEVICE_DESCRIPTORS = byteArray(
        0x05, 0x01, // Usage page (Generic Desktop)
        0x09, 0x06, // Usage (Keyboard)
        0xA1, 0x01, // Collection (Application)
        0x85, 1,    //    Report ID (Keyboad)
        0x05, 0x07, //       Usage page (Key Codes)
        0x19, 0xE0, //       Usage minimum (224)
        0x29, 0xE7, //       Usage maximum (231)
        0x15, 0x00, //       Logical minimum (0)
        0x25, 0x01, //       Logical maximum (1)
        0x75, 0x01, //       Report size (1)
        0x95, 0x08, //       Report count (8)
        0x81, 0x02, //       Input (Data, Variable, Absolute) ; Modifier byte
        0x75, 0x08, //       Report size (8)
        0x95, 0x01, //       Report count (1)
        0x81, 0x01, //       Input (Constant)                 ; Reserved byte
        0x75, 0x08, //       Report size (8)
        0x95, 0x06, //       Report count (6)
        0x15, 0x00, //       Logical Minimum (0)
        0x25, 0x65, //       Logical Maximum (101)
        0x05, 0x07, //       Usage page (Key Codes)
        0x19, 0x00, //       Usage Minimum (0)
        0x29, 0x65, //       Usage Maximum (101)
        0x81, 0x00, //       Input (Data, Array)              ; Key array (6 keys)
        0xC0,       // End Collection

        // Mouse
        0x05, 0x01, // Usage Page (Generic Desktop)
        0x09, 0x02, // Usage (Mouse)
        0xA1, 0x01, // Collection (Application)
        0x85, 2,    //    Report ID (Mouse)
        0x09, 0x01, //    Usage (Pointer)
        0xA1, 0x00, //    Collection (Physical)
        0x05, 0x09, //       Usage Page (Buttons)
        0x19, 0x01, //       Usage minimum (1)
        0x29, 0x03, //       Usage maximum (3)
        0x15, 0x00, //       Logical minimum (0)
        0x25, 0x01, //       Logical maximum (1)
        0x75, 0x01, //       Report size (1)
        0x95, 0x03, //       Report count (3)
        0x81, 0x02, //       Input (Data, Variable, Absolute)
        0x75, 0x05, //       Report size (5)
        0x95, 0x01, //       Report count (1)
        0x81, 0x01, //       Input (constant)                 ; 5 bit padding
        0x05, 0x01, //       Usage page (Generic Desktop)
        0x09, 0x30, //       Usage (X)
        0x09, 0x31, //       Usage (Y)
        0x09, 0x38, //       Usage (Wheel)
        0x15, 0x81, //       Logical minimum (-127)
        0x25, 0x7F, //       Logical maximum (127)
        0x75, 0x08, //       Report size (8)
        0x95, 0x03, //       Report count (3)
        0x81, 0x06, //       Input (Data, Variable, Relative)
        0xC0,       //    End Collection
        0xC0,       // End Collection

        // Battery
        0x05, 0x0C, // Usage page (Consumer)
        0x09, 0x01, // Usage (Consumer Control)
        0xA1, 0x01, // Collection (Application)
        0x85, 32,   //    Report ID (Battery)
        0x05, 0x01, //    Usage page (Generic Desktop)
        0x09, 0x06, //    Usage (Keyboard)
        0xA1, 0x02, //    Collection (Logical)
        0x05, 0x06, //       Usage page (Generic Device Controls)
        0x09, 0x20, //       Usage (Battery Strength)
        0x15, 0x00, //       Logical minimum (0)
        0x26, 0xff, 0x00, // Logical maximum (255)
        0x75, 0x08, //       Report size (8)
        0x95, 0x01, //       Report count (3)
        0x81, 0x02, //       Input (Data, Variable, Absolute)
        0xC0,       //    End Collection
        0xC0,       // End Collection
    )
    /*
    End of source code adapted from ginkage/wearmouse project.
     */

    // https://www.bluetooth.com/specifications/assigned-numbers/
    val PROFILE_UUID_SERIAL_PORT =
        ParcelUuid.fromString("00001101-0000-1000-8000-00805f9b34fb")
    val PROFILE_UUID_HEADSET =
        ParcelUuid.fromString("00001108-0000-1000-8000-00805f9b34fb")
    val PROFILE_UUID_AUDIO_SOURCE =
        ParcelUuid.fromString("0000110a-0000-1000-8000-00805f9b34fb")
    val PROFILE_UUID_AUDIO_SINK =
        ParcelUuid.fromString("0000110b-0000-1000-8000-00805f9b34fb")
    val PROFILE_UUID_REMOTE_CONTROL =
        ParcelUuid.fromString("0000110e-0000-1000-8000-00805f9b34fb")
    val PROFILE_UUID_NETWORK_NODE =
        ParcelUuid.fromString("00001115-0000-1000-8000-00805f9b34fb")
    val PROFILE_UUID_HANDSFREE =
        ParcelUuid.fromString("0000111e-0000-1000-8000-00805f9b34fb")
    val PROFILE_UUID_HANDSFREE_AUDIO_GATEWAY =
        ParcelUuid.fromString("0000111f-0000-1000-8000-00805f9b34fb")
}
