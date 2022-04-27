package com.giphy.ui.list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.giphy.R
import com.giphy.databinding.ActivityGiphyListBinding
import com.giphy.network.model.Giphy
import com.giphy.ui.common.ViewState
import com.giphy.ui.common.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GiphyListActivity : AppCompatActivity(R.layout.activity_giphy_list) {

    private val viewModel: GiphyListViewModel by viewModels()
    private lateinit var binding: ActivityGiphyListBinding
    private lateinit var adapter: GiphyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGiphyListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btnCancel.setOnClickListener {
            binding.editText.apply {
                text?.clear()
                clearFocus()
                hideKeyboard()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.viewState.onEach { state ->
            when(state){
                is ViewState.Loading -> showProgress()
                is ViewState.Data -> showList(state.data)
                is ViewState.Error -> showErrorAlert()
            }
        }.launchIn(lifecycleScope)
    }

    private fun setupRecyclerView() {
        adapter = GiphyListAdapter(listOf(), onClick = { giphy ->
            Toast.makeText(this, giphy.title, Toast.LENGTH_SHORT).show()
        })
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }

    private fun showErrorAlert() {
    }

    private fun showList(data: List<Giphy>) {
        adapter.updateList(data)
    }

    private fun showProgress() {
    }
}