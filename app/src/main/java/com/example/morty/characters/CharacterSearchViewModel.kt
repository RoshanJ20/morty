package com.example.morty.characters

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.morty.Constants
import com.example.morty.Event
import com.example.morty.characters.CharacterSearchPagingSource.LocalException

class CharacterSearchViewModel: ViewModel() {

    private var currentUserSearch: String = ""
    private var pagingSource: CharacterSearchPagingSource? = null
        get(){
            if (field == null || field?.invalid == true){
                field = CharacterSearchPagingSource(currentUserSearch) { localException ->
                    _localExceptionEventLiveData.postValue(Event(localException))
                }
            }
            return field
        }
    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false)
    )
    {
        pagingSource!!
    }.flow.cachedIn(viewModelScope)

    private val _localExceptionEventLiveData = MutableLiveData<Event<LocalException>>()
    val localExceptionEventLiveData: LiveData<Event<LocalException>> = _localExceptionEventLiveData

    fun submitQuery(userSearch: String) {
        currentUserSearch = userSearch
        pagingSource?.invalidate()


    }

}
