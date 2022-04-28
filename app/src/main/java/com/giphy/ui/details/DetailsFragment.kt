package com.giphy.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.giphy.R
import com.giphy.databinding.FragmentDetailsBinding
import com.giphy.network.model.Giphy
import com.giphy.ui.adapters.ViewPagerAdapter
import com.giphy.ui.common.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment: Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsGiphyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.onEach { state ->
            when (state) {
                is ViewState.Data -> setupViewPager(state.data)
               // is ViewState.Error -> showErrorAlert()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupViewPager(data: List<Giphy>) {
        val pagerAdapter = ViewPagerAdapter(requireActivity())
            .apply { setData(data) }

        val pos = requireArguments().getInt(ARG_GIPHY_POSITION)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.setCurrentItem(pos, false)
    }

    companion object {
        const val ARG_GIPHY_POSITION = "arg_giphy_position"
    }
}