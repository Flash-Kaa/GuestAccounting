package com.flasska.guestaccounting.di

import android.content.Context
import androidx.room.Room
import com.flasska.guestaccounting.data.local.GuestAccountingDB
import com.flasska.guestaccounting.data.local.GuestAccountingDao
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
        guestAccountingDao: GuestAccountingDao,
    ): GuestAccountingRepository = GuestAccountingRepositoryImpl(
        databaseDao = guestAccountingDao,
    )

    @Provides
    @Singleton
    fun provideDao(
        context: Context
    ): GuestAccountingDao = Room.databaseBuilder(
        context = context,
        klass = GuestAccountingDB::class.java,
        name = "wedding_guests_db"
    ).build().getDao()
}
