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

    init {
        getGiphyList()
    }

    private fun getGiphyList() = viewModelScope.launch {
        try {
            viewState.value = ViewState.Loading
            val list = repository.getGiphyList()
            viewState.value = ViewState.Data(list)
        } catch (e: Exception){
            viewState.value = ViewState.Error(e)
        }
    }
}