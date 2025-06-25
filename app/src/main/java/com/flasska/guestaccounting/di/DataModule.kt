package com.flasska.guestaccounting.di

import com.flasska.guestaccounting.data.repository.GuestAccountingRepositoryImpl
import com.flasska.guestaccounting.domain.interfaces.GuestAccountingRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DataModule {

    @Singleton
    @Provides
    fun provideGuestAccountingRepository(
    ): GuestAccountingRepository = GuestAccountingRepositoryImpl(
    )
}
