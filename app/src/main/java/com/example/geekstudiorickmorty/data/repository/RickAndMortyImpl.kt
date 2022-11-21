package com.example.geekstudiorickmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.geekstudiorickmorty.data.local.RickAndMortyDao
import com.example.geekstudiorickmorty.data.remote.RickyAndMortyApi
import com.example.geekstudiorickmorty.data.remote.dto.character.CharacterData
import com.example.geekstudiorickmorty.domain.model.Characters
import com.example.geekstudiorickmorty.domain.repository.RickAndMortyRepository
import com.example.geekstudiorickmorty.paging.CharactersPagingDataSource
import com.example.geekstudiorickmorty.util.GenderState
import com.example.geekstudiorickmorty.util.StatusState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyImpl @Inject constructor(
    val api: RickyAndMortyApi,
    private val dao: RickAndMortyDao
) : RickAndMortyRepository {

    override suspend fun getAllCharacters(
        status: StatusState,
        gender: GenderState,
        name: String
    ): Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                CharactersPagingDataSource(
                    api,
                    statusState = status,
                    genderState = gender,
                    nameQuery = name
                )
            }
        ).flow
    }

    override suspend fun getCharacterDetailById(characterId: Int): CharacterData {
        return api.getCharacter(characterId)
    }

    override suspend fun getAllFavoriteCharacters(): Flow<List<Characters>> {
        return dao.getAllFavoriteCharacters()
    }

    override suspend fun insertMyFavoriteList(character: Characters) {
        dao.insertFavoriteCharacter(character = character)
    }

    override suspend fun deleteCharacterFromMyFavoriteList(character: Characters) {
        dao.deleteFavoriteCharacter(character)
    }

}