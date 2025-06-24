package com.flasska.guestaccounting.di

import android.content.Context
import com.flasska.guestaccounting.presentation.map.MapViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [VMFactoryModule::class])
internal interface AppComponent {

    fun provideMapViewModelFactory(): MapViewModel.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
