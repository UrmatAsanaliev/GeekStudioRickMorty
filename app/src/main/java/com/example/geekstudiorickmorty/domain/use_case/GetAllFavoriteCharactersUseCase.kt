package com.example.geekstudiorickmorty.domain.use_case

import com.example.geekstudiorickmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class GetAllFavoriteCharactersUseCase @Inject constructor(
    private val repo: RickAndMortyRepository
){

    suspend fun getAllFavoriteCharacters() = repo.getAllFavoriteCharacters()
}