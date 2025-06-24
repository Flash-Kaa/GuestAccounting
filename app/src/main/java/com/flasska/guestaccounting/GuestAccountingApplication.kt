package com.flasska.guestaccounting

import android.app.Application
import com.flasska.guestaccounting.di.AppComponent
import com.flasska.guestaccounting.di.DaggerAppComponent

internal class GuestAccountingApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}
