package com.example.geekstudiorickmorty.domain.use_case

import com.example.geekstudiorickmorty.domain.repository.RickAndMortyRepository
import com.example.geekstudiorickmorty.util.GenderState
import com.example.geekstudiorickmorty.util.StatusState
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val repo: RickAndMortyRepository
){

    suspend fun getAllCharacters(status: StatusState, gender: GenderState, name: String) =
        repo.getAllCharacters(status, gender, name)
}