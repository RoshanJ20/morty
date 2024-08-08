package com.example.morty.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.morty.domain.models.Character

class CharacterViewModel: ViewModel() {

    private val repository = CharacterRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun fetchCharacter(characterId: Int) =
        viewModelScope.launch {
            val character = repository.getCharacterById(characterId)!!
            _characterByIdLiveData.postValue(character)
        }
    }
