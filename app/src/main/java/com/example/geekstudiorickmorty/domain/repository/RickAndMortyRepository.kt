package com.example.geekstudiorickmorty.domain.repository

import androidx.paging.PagingData
import com.example.geekstudiorickmorty.data.remote.dto.character.CharacterData
import com.example.geekstudiorickmorty.data.remote.dto.episode.EpisodeResult
import com.example.geekstudiorickmorty.data.remote.dto.location.LocationResults
import com.example.geekstudiorickmorty.domain.model.CharactersDomain
import com.example.geekstudiorickmorty.domain.model.EpisodeDomain
import com.example.geekstudiorickmorty.domain.model.LocationDomain
import com.example.geekstudiorickmorty.util.GenderState
import com.example.geekstudiorickmorty.util.StatusState
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

interface RickAndMortyRepository {

    suspend fun getAllCharacters(
        status: StatusState = StatusState.NONE,
        gender: GenderState = GenderState.NONE,
        name: String = ""
    ): Flow<PagingData<CharacterData>>


    suspend fun getCharacterDetailById(characterId: Int): CharacterData

    suspend fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>>

    suspend fun insertMyFavoriteList(character: CharactersDomain)

    suspend fun deleteCharacterFromMyFavoriteList(character: CharactersDomain)
}