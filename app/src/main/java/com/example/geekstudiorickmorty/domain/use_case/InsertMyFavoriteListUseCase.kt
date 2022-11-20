package com.example.geekstudiorickmorty.domain.use_case

import com.example.geekstudiorickmorty.domain.model.Characters
import com.example.geekstudiorickmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class InsertMyFavoriteListUseCase @Inject constructor(
    private val repo: RickAndMortyRepository
) {


    suspend fun insertMyFavoriteCharacters(characters: Characters) =
        repo.insertMyFavoriteList(characters)
}