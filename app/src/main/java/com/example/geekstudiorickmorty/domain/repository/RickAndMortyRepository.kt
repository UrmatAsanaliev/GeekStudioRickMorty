package com.example.geekstudiorickmorty.domain.repository

import androidx.paging.PagingData
import com.example.geekstudiorickmorty.data.remote.dto.character.CharacterData
import com.example.geekstudiorickmorty.domain.model.Characters
import com.example.geekstudiorickmorty.util.GenderState
import com.example.geekstudiorickmorty.util.StatusState
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    suspend fun getAllCharacters(
        status: StatusState = StatusState.NONE,
        gender: GenderState = GenderState.NONE,
        name: String = ""
    ): Flow<PagingData<CharacterData>>


    suspend fun getCharacterDetailById(characterId: Int): CharacterData

    suspend fun getAllFavoriteCharacters(): Flow<List<Characters>>

    suspend fun insertMyFavoriteList(character: Characters)

    suspend fun deleteCharacterFromMyFavoriteList(character: Characters)
}