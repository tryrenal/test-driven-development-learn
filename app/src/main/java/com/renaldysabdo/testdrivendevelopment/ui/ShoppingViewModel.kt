package com.renaldysabdo.testdrivendevelopment.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.renaldysabdo.testdrivendevelopment.repositories.ShopRepository

class ShoppingViewModel @ViewModelInject constructor(
    private val repository: ShopRepository
): ViewModel() {
}