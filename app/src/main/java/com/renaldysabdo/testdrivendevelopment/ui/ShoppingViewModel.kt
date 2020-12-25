package com.renaldysabdo.testdrivendevelopment.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingItem
import com.renaldysabdo.testdrivendevelopment.data.remote.responses.ImageResponse
import com.renaldysabdo.testdrivendevelopment.other.Event
import com.renaldysabdo.testdrivendevelopment.other.Resource
import com.renaldysabdo.testdrivendevelopment.repositories.ShopRepository
import kotlinx.coroutines.launch

class ShoppingViewModel @ViewModelInject constructor(
    private val repository: ShopRepository
): ViewModel() {

    val shoppingItems = repository.observeAllShoppingItems()
    val totalPrice = repository.observeTotalPrice()

    //untuk mengambil nilai dari image
    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images : LiveData<Event<Resource<ImageResponse>>> = _images

    //untuk mengambil nilai dari image yang dipilih
    private val _curImage = MutableLiveData<String>()
    val curImage : LiveData<String> = _curImage

    //untuk mengetahui status dari data yang akan di masukkan dalam shopping item
    private val _shoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val shoppingItemStatus : LiveData<Event<Resource<ShoppingItem>>> = _shoppingItemStatus

    fun setCurImageUrl(url: String){
        _curImage.postValue(url)
    }

    fun deleteShoppingItem(item: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(item)
    }

    fun insertShoppingItemToDb(item: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(item)
    }

    fun createShoppingItem(name: String, amount: String, price: String){

    }

    fun searchImageQuery(query: String){

    }


}