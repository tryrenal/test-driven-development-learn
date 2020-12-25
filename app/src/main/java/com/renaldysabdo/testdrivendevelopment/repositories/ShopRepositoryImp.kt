package com.renaldysabdo.testdrivendevelopment.repositories

import androidx.lifecycle.LiveData
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingDao
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingItem
import com.renaldysabdo.testdrivendevelopment.data.remote.ApiService
import com.renaldysabdo.testdrivendevelopment.data.remote.responses.ImageResponse
import com.renaldysabdo.testdrivendevelopment.other.Resource
import javax.inject.Inject

class ShopRepositoryImp @Inject constructor(
    private val shopDao : ShoppingDao,
    private val api : ApiService
) : ShopRepository {

    override suspend fun insertShoppingItem(item: ShoppingItem) {
        shopDao.insertShoppingItem(item)
    }

    override suspend fun deleteShoppingItem(item: ShoppingItem) {
        shopDao.deleteShoppingItem(item)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return  shopDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shopDao.observeTotalPrice()
    }

    override suspend fun searchImageItem(imageQuery: String): Resource<ImageResponse> {
        return try {
            val result = api.searchImage(imageQuery = imageQuery)
            if (result.isSuccessful){
                result.body()?.let {
                    Resource.success(it)
                } ?: Resource.error("error unknown", null)
            } else {
                Resource.error("error unknown", null)
            }
        }catch (e: Exception){
            Resource.error(e.message.toString(), null)
        }
    }
}