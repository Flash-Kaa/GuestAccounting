package com.flasska.guestaccounting.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.flasska.guestaccounting.GuestAccountingApplication
import com.flasska.guestaccounting.di.AppComponent

internal val Context.appComponent: AppComponent
    get() = when (this) {
        is GuestAccountingApplication -> this.appComponent
        else -> this.applicationContext.appComponent
    }

internal val LocalAppComponent: AppComponent
    @Composable get() = LocalContext.current.appComponent
