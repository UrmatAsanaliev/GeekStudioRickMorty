package com.example.geekstudiorickmorty.presentation.character.viewmodel.states

import com.example.geekstudiorickmorty.data.remote.dto.character.CharacterData

data class CharacterDetailState(
    val character: CharacterData? = null,
    val characterIdFromCharacterListFragment: Int = 1,
    val navigateArgLocationId: Int? = null,
    val isLoadingEpisodeInfo: Boolean = false
)