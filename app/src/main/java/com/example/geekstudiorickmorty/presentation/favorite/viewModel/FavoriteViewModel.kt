package com.example.geekstudiorickmorty.presentation.favorite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geekstudiorickmorty.domain.model.Characters
import com.example.geekstudiorickmorty.domain.repository.RickAndMortyRepository
import com.example.geekstudiorickmorty.domain.use_case.DeleteCharacterFromMyFavoriteListUseCase
import com.example.geekstudiorickmorty.domain.use_case.GetAllFavoriteCharactersUseCase
import com.example.geekstudiorickmorty.presentation.favorite.FavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteCharactersUseCase: GetAllFavoriteCharactersUseCase,
    private val deleteCharacterFromMyFavoriteListUseCase: DeleteCharacterFromMyFavoriteListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> get() = _state


    init {
        viewModelScope.launch {
            try {
                getFavoriteCharacters().collect {
                    _state.value = _state.value.copy(
                        characterList = it,
                        isError = true
                    )
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    characterList = emptyList(),
                    isError = true
                )
            }
        }
    }

    suspend fun getFavoriteCharacters(): Flow<List<Characters>> {
        return getAllFavoriteCharactersUseCase.getAllFavoriteCharacters()
    }

    fun deleteCharacter(charactersDomain: Characters) {
        viewModelScope.launch {
            deleteCharacterFromMyFavoriteListUseCase.deleteCharacterFromMyFavoriteList(charactersDomain)
        }
    }

}