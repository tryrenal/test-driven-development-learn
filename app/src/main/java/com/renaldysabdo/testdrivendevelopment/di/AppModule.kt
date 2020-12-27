package com.renaldysabdo.testdrivendevelopment.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.renaldysabdo.testdrivendevelopment.R
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingDao
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingItemDatabase
import com.renaldysabdo.testdrivendevelopment.data.remote.ApiService
import com.renaldysabdo.testdrivendevelopment.other.Constanta.BASE_URL
import com.renaldysabdo.testdrivendevelopment.other.Constanta.DATABASE_NAME
import com.renaldysabdo.testdrivendevelopment.repositories.ShopRepository
import com.renaldysabdo.testdrivendevelopment.repositories.ShopRepositoryImp
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
    fun provideRepository(
        shopDao : ShoppingDao,
        api : ApiService
    ) = ShopRepositoryImp(shopDao, api) as ShopRepository

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

    @Provides
    @Singleton
    fun provideGlideInstance(
            @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
    )
}