package com.giphy.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.flatMap
import com.giphy.api.model.Giphy
import com.giphy.data.model.GiphyEntity
import com.giphy.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiphyListViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    private val _showGiphyList = MutableStateFlow<Boolean?>(null)
    private val showGiphyList: StateFlow<Boolean?> = _showGiphyList.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val giphy: StateFlow<PagingData<GiphyEntity>> = showGiphyList
        .map { repository.getGiphyList(it) }
        .filterNotNull()
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    @OptIn(ExperimentalCoroutinesApi::class)
    val searchGiphy: StateFlow<PagingData<GiphyEntity>> = query
        .map{ repository.getGiphyByQuery(it) }
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun setQuery(query: String) {
        _query.tryEmit(query)
    }

    fun getGiphyList(){
        _showGiphyList.tryEmit(true)
        _showGiphyList.tryEmit(null)
    }

    fun removeSelectedGiphyFromDb(list: List<Giphy>, query: String) = viewModelScope.launch {
        val ids = list.map { it.id }
        repository.removeSelectedGiphyFromDb(ids)

        if (query.isEmpty())
            getGiphyList()
        else
            setQuery(query)
    }
}