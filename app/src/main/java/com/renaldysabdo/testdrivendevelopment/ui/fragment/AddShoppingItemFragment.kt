package com.renaldysabdo.testdrivendevelopment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.renaldysabdo.testdrivendevelopment.R
import com.renaldysabdo.testdrivendevelopment.databinding.FragmentAddShoppingItemBinding
import com.renaldysabdo.testdrivendevelopment.ui.ShoppingViewModel
import javax.inject.Inject

class AddShoppingItemFragment @Inject constructor(
    val glide : RequestManager
) : Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var shopViewModel : ShoppingViewModel

    private lateinit var binding : FragmentAddShoppingItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddShoppingItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        binding.ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }

        //karena kita menggunakan satu viewModel maka kita perlu untuk mengeset current image menjadi empty kembali
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                shopViewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}