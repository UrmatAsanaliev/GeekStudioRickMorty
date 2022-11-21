package com.example.geekstudiorickmorty.data.local

import androidx.paging.PagingData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.geekstudiorickmorty.domain.model.Characters
import kotlinx.coroutines.flow.Flow

@Dao
interface RickAndMortyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCharacter(character: Characters)

    @Delete
    suspend fun deleteFavoriteCharacter(character: Characters)

    @Query("SELECT * FROM characters")
    fun getAllFavoriteCharacters(): Flow<List<Characters>>
}