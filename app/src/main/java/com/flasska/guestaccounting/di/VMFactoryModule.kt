package com.flasska.guestaccounting.di

import com.flasska.guestaccounting.presentation.guest_creator.GuestCreatorViewModel
import com.flasska.guestaccounting.presentation.map.MapViewModel
import com.flasska.guestaccounting.presentation.table_creator.TableCreatorViewModel
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

    @Singleton
    @Provides
    fun provideTableCreatorViewModel(
    ) = TableCreatorViewModel.Factory(
    )

    @Singleton
    @Provides
    fun provideGuestCreatorViewModel(
    ) = GuestCreatorViewModel.Factory(
    )
}
