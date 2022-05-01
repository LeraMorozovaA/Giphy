package com.giphy.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.giphy.R
import com.giphy.api.mapper.toModel
import com.giphy.databinding.FragmentGiphyListBinding
import com.giphy.api.model.Giphy
import com.giphy.ui.adapters.GiphyListAdapter
import com.giphy.ui.common.ViewState
import com.giphy.ui.common.hideKeyboard
import com.giphy.ui.common.showAlert
import com.giphy.ui.details.DetailsFragment.Companion.ARG_GIPHY_POSITION
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GiphyListFragment: Fragment(R.layout.fragment_giphy_list) {

    private val viewModel: GiphyListViewModel by viewModels()
    private var adapter: GiphyListAdapter? = null
    private var shouldUpdateList = true
    private lateinit var binding: FragmentGiphyListBinding

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
        collectUiState()

        if (shouldUpdateList){
            viewModel.getGiphyList()
            shouldUpdateList = false
        }
    }

    private fun setupRecyclerView() {
        adapter = GiphyListAdapter(onClick = { id ->
            val bundle = bundleOf(ARG_GIPHY_POSITION to id)
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
                viewModel.getGiphyList()
            }
        }

        binding.editText.doBeforeTextChanged { _, _, _, after ->
            if (after == 0)
                viewModel.getGiphyList()
        }

        binding.editText.doAfterTextChanged {
            if (it.isNullOrBlank())
                return@doAfterTextChanged

            viewModel.setQuery(it.toString())
        }

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            viewModel.getGiphyList()
        }

        adapter?.addLoadStateListener { state ->
            binding.progress.isVisible = state.append is LoadState.Loading
            if (state.refresh is LoadState.Error)
                showErrorAlert()
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchGiphy.collectLatest { list ->
                val data = list.map { it.toModel() }
                adapter?.submitData(data)
                binding.recyclerView.scrollToPosition(0)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.giphy.collectLatest { list ->
                val data = list.map { it.toModel() }
                adapter?.submitData(data)
                binding.recyclerView.scrollToPosition(0)
            }
        }
    }

    private fun showErrorAlert() {
        MaterialAlertDialogBuilder(requireContext()).showAlert(
            title = getString(R.string.alert_title),
            message = getString(R.string.alert_message),
            textButton = getString(R.string.alert_button)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}