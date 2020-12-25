package com.renaldysabdo.testdrivendevelopment.di

import android.content.Context
import androidx.room.Room
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingItemDatabase
import com.renaldysabdo.testdrivendevelopment.data.remote.ApiService
import com.renaldysabdo.testdrivendevelopment.other.Constanta.BASE_URL
import com.renaldysabdo.testdrivendevelopment.other.Constanta.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, ShoppingItemDatabase::class.java, DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()
    
     @Provides
     @Singleton
     fun provideApiService(): ApiService {
         return Retrofit.Builder()
             .addConverterFactory(GsonConverterFactory.create())
             .baseUrl(BASE_URL)
             .build()
             .create(ApiService::class.java)
     }
}