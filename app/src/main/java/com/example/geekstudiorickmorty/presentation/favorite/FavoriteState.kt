package com.example.geekstudiorickmorty.presentation.favorite

import com.example.geekstudiorickmorty.domain.model.CharactersDomain

data class FavoriteState(
    val characterList: List<CharactersDomain> = emptyList(),
    val isError: Boolean = false
)