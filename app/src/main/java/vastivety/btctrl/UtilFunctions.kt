package vastivety.btctrl

import android.content.Intent
import android.os.Build

object UtilFunctions {

    fun byteArray(vararg ints: Int) = ints.map { it.toByte() }.toByteArray()

    fun <T>Intent.getParcelableExtraPolyfill(name: String?, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) // API level 33
            this.getParcelableExtra(name, clazz)
        else
            @Suppress("DEPRECATION")
            this.getParcelableExtra(name) // deprecated in API level 33
    }
}
