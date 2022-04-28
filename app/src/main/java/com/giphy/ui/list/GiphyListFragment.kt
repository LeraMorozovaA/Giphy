package com.giphy.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.giphy.R
import com.giphy.databinding.FragmentGiphyListBinding
import com.giphy.network.model.Giphy
import com.giphy.ui.adapters.GiphyListAdapter
import com.giphy.ui.common.ViewState
import com.giphy.ui.common.hideKeyboard
import com.giphy.ui.details.DetailsFragment.Companion.ARG_GIPHY_POSITION
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GiphyListFragment: Fragment(R.layout.fragment_giphy_list) {

    private val viewModel: GiphyListViewModel by viewModels()
    private lateinit var binding: FragmentGiphyListBinding
    private lateinit var adapter: GiphyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGiphyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupListeners()
        setupObserving()
    }

    private fun setupRecyclerView() {
        adapter = GiphyListAdapter(listOf(), onClick = { position ->
            val bundle = bundleOf(ARG_GIPHY_POSITION to position)
            findNavController().navigate(R.id.action_giphyListFragment_to_detailsFragment, bundle)

        })
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnCancel.setOnClickListener {
            binding.editText.apply {
                text?.clear()
                clearFocus()
                hideKeyboard()
            }
        }
    }

    private fun setupObserving() {
        viewModel.viewState.onEach { state ->
            when (state) {
                is ViewState.Loading -> showProgress()
                is ViewState.Data -> showList(state.data)
                is ViewState.Error -> showErrorAlert()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showErrorAlert() {
    }

    private fun showList(data: List<Giphy>) {
        adapter.updateList(data)
    }

    private fun showProgress() {
    }
}