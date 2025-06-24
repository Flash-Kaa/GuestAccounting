package com.flasska.guestaccounting.di

import com.flasska.guestaccounting.presentation.map.MapViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class VMFactoryModule {

    @Singleton
    @Provides
    fun provideMapViewModelFactory(
    ) = MapViewModel.Factory(
    )
}
