package com.giphy.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giphy.api.model.Giphy
import com.giphy.repository.GiphyRepository
import com.giphy.ui.common.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailsGiphyViewModel @Inject constructor(
    private val repository: GiphyRepository
): ViewModel() {

    val viewState = MutableStateFlow<ViewState<List<Giphy>>>(ViewState.Idle)
    val giphy = MutableStateFlow<Giphy?>(null)

    init {
        getGiphyListFromDb()
    }

    suspend fun getGiphyPositionInListById(id: String): Int {
        return repository.getGiphyListFromDb().indexOfFirst { it.id == id }
    }

    fun getGiphyById(id: String) = viewModelScope.launch {
        giphy.value = repository.getGiphyListFromDb().find { it.id == id }
    }

    private fun getGiphyListFromDb() = viewModelScope.launch {
        try {
            val list = repository.getGiphyListFromDb()
            viewState.value = ViewState.Data(list)
        } catch (e: Exception){
            viewState.value = ViewState.Error
        }
    }
}