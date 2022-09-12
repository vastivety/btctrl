package vastivety.btctrl

import android.app.Application
import com.google.android.material.color.DynamicColors

class BtctrlApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
