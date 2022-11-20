package com.example.geekstudiorickmorty.presentation.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geekstudiorickmorty.domain.repository.RickAndMortyRepository
import com.example.geekstudiorickmorty.domain.use_case.GetCharacterDetailByIdUseCase
import com.example.geekstudiorickmorty.presentation.character.viewmodel.states.CharacterDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class   CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailByIdUseCase: GetCharacterDetailByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state: StateFlow<CharacterDetailState> get() = _state


    private fun getCharacter(characterID: Int) {

        viewModelScope.launch {
            val data = getCharacterDetailByIdUseCase.getCharactersDetails(characterID)

            _state.value = _state.value.copy(
                character = data
            )

            val episodeList = _state.value.character!!.episode

            episodeList.let {
                _state.value = _state.value.copy(isLoadingEpisodeInfo = true)
                episodeList.forEachIndexed { _, _ ->
                }

                _state.value = _state.value.copy(
                    isLoadingEpisodeInfo = false
                )
            }
        }
    }

    fun setCharacterId(id: Int) {
        _state.value = _state.value.copy(
            characterIdFromCharacterListFragment = id
        )
    }


    fun getCharacterInvoke() {
        getCharacter(getCharacterIDFromFragmentList())
    }

    private fun getCharacterIDFromFragmentList(): Int {
        return _state.value.characterIdFromCharacterListFragment
    }
}

