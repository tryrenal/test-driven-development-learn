package com.renaldysabdo.testdrivendevelopment.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.renaldysabdo.testdrivendevelopment.R
import com.renaldysabdo.testdrivendevelopment.ui.ShoppingViewModel

class ImagePickFragment : Fragment(R.layout.fragment_image_pick) {

    lateinit var shopViewModel : ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }
}