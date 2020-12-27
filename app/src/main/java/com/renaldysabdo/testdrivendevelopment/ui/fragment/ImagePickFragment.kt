package com.renaldysabdo.testdrivendevelopment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.renaldysabdo.testdrivendevelopment.R
import com.renaldysabdo.testdrivendevelopment.adapter.ImageAdapter
import com.renaldysabdo.testdrivendevelopment.databinding.FragmentImagePickBinding
import com.renaldysabdo.testdrivendevelopment.other.Constanta.GRID_SPAN_COUNT
import com.renaldysabdo.testdrivendevelopment.ui.ShoppingViewModel
import javax.inject.Inject

class ImagePickFragment @Inject constructor(
        private val imageAdapter: ImageAdapter
): Fragment(R.layout.fragment_image_pick) {

    lateinit var shopViewModel : ShoppingViewModel

    private lateinit var binding: FragmentImagePickBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentImagePickBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        setupRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            shopViewModel.setCurImageUrl(it)
        }
    }

    private fun setupRecyclerView(){
        binding.rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }
    }
}