package com.example.geekstudiorickmorty.domain.use_case

import com.example.geekstudiorickmorty.domain.model.Characters
import com.example.geekstudiorickmorty.domain.repository.RickAndMortyRepository
import javax.inject.Inject

class DeleteCharacterFromMyFavoriteListUseCase @Inject constructor(
    private val repo: RickAndMortyRepository
){

    suspend fun deleteCharacterFromMyFavoriteList(character: Characters) =
        repo.deleteCharacterFromMyFavoriteList(character)


}