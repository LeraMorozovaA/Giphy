package com.giphy.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giphy.network.model.Giphy
import com.giphy.repository.GiphyRepository
import com.giphy.ui.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class GiphyListViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    val viewState = MutableStateFlow<ViewState<List<Giphy>>>(ViewState.Idle)
    val giphyList = MutableStateFlow<List<Giphy>>(listOf())

    init {
        getGiphyList()
    }

    fun searchGiphyByQuery(query: String) = viewModelScope.launch {
        try {
            viewState.value = ViewState.Loading
            val result = repository.getGiphyByQuery(query)
            viewState.value = ViewState.Data(result)
        } catch (e: Exception) {
            viewState.value = ViewState.Error(e)
        }
    }

    private fun getGiphyList() = viewModelScope.launch {
        try {
            viewState.value = ViewState.Loading
            val list = repository.getGiphyList()
            giphyList.value = list
            viewState.value = ViewState.Data(list)
        } catch (e: Exception) {
            viewState.value = ViewState.Error(e)
        }
    }
}