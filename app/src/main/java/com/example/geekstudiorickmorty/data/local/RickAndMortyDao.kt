package com.example.geekstudiorickmorty.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.geekstudiorickmorty.domain.model.Characters
import kotlinx.coroutines.flow.Flow

@Dao
interface RickAndMortyDao {

    @Insert
    suspend fun insertFavoriteCharacter(character: Characters)

    @Delete
    suspend fun deleteFavoriteCharacter(character: Characters)

    @Query("SELECT * FROM characters")
    fun getAllFavoriteCharacters(): Flow<List<Characters>>
}