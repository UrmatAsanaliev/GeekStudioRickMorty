package com.example.geekstudiorickmorty.domain.use_case

import com.example.geekstudiorickmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class GetCharacterDetailByIdUseCase @Inject constructor(
    private val repo: RickAndMortyRepository
) {


    suspend fun getCharactersDetails(id: Int) = repo.getCharacterDetailById(id)
}