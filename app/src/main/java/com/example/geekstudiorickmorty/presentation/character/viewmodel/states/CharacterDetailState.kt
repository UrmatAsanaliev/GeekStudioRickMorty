package com.example.geekstudiorickmorty.presentation.character.viewmodel.states

import com.example.geekstudiorickmorty.data.remote.dto.character.CharacterData
import com.example.geekstudiorickmorty.domain.model.EpisodeDomain

data class CharacterDetailState(
    val character: CharacterData? = null,
    val characterIdFromCharacterListFragment: Int = 1,
    val navigateArgLocationId: Int? = null,
    val episodeList: List<EpisodeDomain>? = null,
    val isLoadingEpisodeInfo: Boolean = false
)