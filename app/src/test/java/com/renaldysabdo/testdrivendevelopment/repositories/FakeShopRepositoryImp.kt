package com.renaldysabdo.testdrivendevelopment.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingItem
import com.renaldysabdo.testdrivendevelopment.data.remote.responses.ImageResponse
import com.renaldysabdo.testdrivendevelopment.other.Resource


//di buat bukan untuk menguji repository akan tetapi untuk menguji viewModel
class FakeShopRepositoryImp : ShopRepository {

    private val shoppingItem = mutableListOf<ShoppingItem>()

    private val observeShoppingItem = MutableLiveData<List<ShoppingItem>>(shoppingItem)
    private val observeTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
        observeShoppingItem.postValue(shoppingItem)
        observeTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice() : Float {
        return shoppingItem.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(item: ShoppingItem) {
        shoppingItem.add(item)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(item: ShoppingItem) {
        shoppingItem.remove(item)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observeShoppingItem
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observeTotalPrice
    }

    override suspend fun searchImageItem(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }
}