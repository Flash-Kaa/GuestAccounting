package com.flasska.guestaccounting.di

import com.flasska.guestaccounting.domain.interfaces.GuestAccountingRepository
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
        guestAccountingRepository: GuestAccountingRepository,
    ) = MapViewModel.Factory(
        guestAccountingRepository = guestAccountingRepository,
    )

    @Singleton
    @Provides
    fun provideTableCreatorViewModel(
        guestAccountingRepository: GuestAccountingRepository,
    ) = TableCreatorViewModel.Factory(
        guestAccountingRepository = guestAccountingRepository,
    )

    @Singleton
    @Provides
    fun provideGuestCreatorViewModel(
        guestAccountingRepository: GuestAccountingRepository,
    ) = GuestCreatorViewModel.Factory(
        guestAccountingRepository = guestAccountingRepository,
    )
}
