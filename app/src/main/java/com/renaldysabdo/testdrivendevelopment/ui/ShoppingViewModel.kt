package com.renaldysabdo.testdrivendevelopment.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renaldysabdo.testdrivendevelopment.data.local.ShoppingItem
import com.renaldysabdo.testdrivendevelopment.data.remote.responses.ImageResponse
import com.renaldysabdo.testdrivendevelopment.other.Constanta
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

    fun createShoppingItem(name: String, amountString: String, priceString: String){
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()){
            _shoppingItemStatus.postValue(Event(Resource.error("empty field", null)))
            return
        }

        if (name.length > Constanta.MAX_NAME_LENGTH){
            _shoppingItemStatus.postValue(Event(Resource.error("long name", null)))
            return
        }

        if (priceString.length > Constanta.MAX_PRICE_LENGTH){
            _shoppingItemStatus.postValue(Event(Resource.error("long price",null)))
            return
        }

        val amount = try {
            amountString.toInt()
        } catch (e: Exception){
            _shoppingItemStatus.postValue(Event(Resource.error("not valid amount", null)))
            return
        }

        val price = try {
            priceString.toFloat()
        } catch (e: Exception){
            _shoppingItemStatus.postValue(Event(Resource.error("not valid price", null)))
            return
        }

        val items = ShoppingItem(name, amount, price, _curImage.value ?: "")
        insertShoppingItemToDb(items)
        setCurImageUrl("")
        _shoppingItemStatus.postValue(Event(Resource.success(items)))
    }

    fun searchImageQuery(query: String){
        if (query.isEmpty()){
            _images.postValue(Event(Resource.error("not valid image query", null)))
            return
        }
        try {
            viewModelScope.launch {
                val response = repository.searchImageItem(query)
                _images.postValue(Event(response))
            }
        }catch (e: Exception){
            _images.postValue(Event(Resource.error(e.message.toString(), null)))
            return
        }
    }

}