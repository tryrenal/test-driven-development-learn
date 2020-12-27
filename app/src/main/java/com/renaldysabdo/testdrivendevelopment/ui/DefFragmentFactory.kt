package com.renaldysabdo.testdrivendevelopment.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.renaldysabdo.testdrivendevelopment.adapter.ImageAdapter
import com.renaldysabdo.testdrivendevelopment.ui.fragment.ImagePickFragment
import javax.inject.Inject

class DefFragmentFactory @Inject constructor(
        private val imageAdapter: ImageAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}