package com.renaldysabdo.testdrivendevelopment.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.renaldysabdo.testdrivendevelopment.adapter.ImageAdapter
import com.renaldysabdo.testdrivendevelopment.adapter.ShoppingItemAdapter
import com.renaldysabdo.testdrivendevelopment.repositories.FakeShopRepositoryAndroidTestImpl
import com.renaldysabdo.testdrivendevelopment.ui.fragment.AddShoppingItemFragment
import com.renaldysabdo.testdrivendevelopment.ui.fragment.ImagePickFragment
import com.renaldysabdo.testdrivendevelopment.ui.fragment.ShoppingFragment
import javax.inject.Inject

class DefFragmentFactoryTest @Inject constructor(
        private val imageAdapter: ImageAdapter,
        private val glide: RequestManager,
        private val shoppingAdapter : ShoppingItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(shoppingAdapter, ShoppingViewModel(FakeShopRepositoryAndroidTestImpl()))
            else -> super.instantiate(classLoader, className)
        }
    }
}