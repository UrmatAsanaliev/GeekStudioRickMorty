package com.example.geekstudiorickmorty.presentation.favorite

import com.example.geekstudiorickmorty.domain.model.Characters

data class FavoriteState(
    val characterList: List<Characters> = emptyList(),
    val isError: Boolean = false
)