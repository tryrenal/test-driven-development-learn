package com.renaldysabdo.testdrivendevelopment.di

import android.content.Context
import androidx.room.Room
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object AppModuleTest {

    @Provides
    @Named("test_db")
    fun provideInMemoryDatabase(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        context, ShoppingItemDatabase::class.java
    ).allowMainThreadQueries()
        .build()
}