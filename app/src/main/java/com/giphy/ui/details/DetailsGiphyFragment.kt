package com.giphy.ui.details

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.giphy.R
import com.giphy.databinding.FragmentDetailsGiphyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsGiphyFragment: Fragment(R.layout.fragment_details_giphy) {

    private lateinit var binding: FragmentDetailsGiphyBinding
    private val viewModel: DetailsGiphyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsGiphyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserving()

        val id = requireArguments().getString(ARG_ID, "")
        viewModel.getGiphyById(id)
    }

    private fun setupObserving() {
        viewModel.giphy.onEach { giphy ->
            giphy ?: return@onEach

            Glide.with(requireContext())
                .asGif()
                .load(giphy.images.original.url)
                .into(binding.imageView)

        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun getInstance(id: String) = DetailsGiphyFragment().apply {
            arguments = bundleOf(ARG_ID to id)
        }
    }
}