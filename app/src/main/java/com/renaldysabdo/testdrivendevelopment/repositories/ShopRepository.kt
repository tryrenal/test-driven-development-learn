package com.renaldysabdo.testdrivendevelopment.repositories

import androidx.lifecycle.LiveData
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingItem
import com.renaldysabdo.testdrivendevelopment.data.remote.responses.ImageResponse
import com.renaldysabdo.testdrivendevelopment.other.Resource

interface ShopRepository {

    suspend fun insertShoppingItem(item: ShoppingItem)

    suspend fun deleteShoppingItem(item: ShoppingItem)

    fun observeAllShoppingItems() : LiveData<List<ShoppingItem>>

    fun observeTotalPrice() : LiveData<Float>

    suspend fun searchImageItem(imageQuery : String) : Resource<ImageResponse>
}