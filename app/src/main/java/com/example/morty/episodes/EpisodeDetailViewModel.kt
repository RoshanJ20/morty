package com.example.morty.episodes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morty.domain.models.Episode
import kotlinx.coroutines.launch

class EpisodeDetailViewModel: ViewModel() {
    private val repository = EpisodeRepository()

    private var _episodeLiveData = MutableLiveData<Episode>()
    val episodeLiveData get() = _episodeLiveData

    fun fetchEpisode(episodeId: Int) {
        viewModelScope.launch {
            val episode = repository.getEpisodeById(episodeId)

            if(episode != null) {
                _episodeLiveData.postValue(episode)
            }
        }
    }
}