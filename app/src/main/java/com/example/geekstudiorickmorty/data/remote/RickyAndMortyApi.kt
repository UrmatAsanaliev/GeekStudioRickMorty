package com.example.geekstudiorickmorty.data.remote

import com.example.geekstudiorickmorty.data.remote.dto.character.CharacterData
import com.example.geekstudiorickmorty.data.remote.dto.character.CharactersDto
import com.example.geekstudiorickmorty.data.remote.dto.episode.EpisodeDto
import com.example.geekstudiorickmorty.data.remote.dto.episode.EpisodeResult
import com.example.geekstudiorickmorty.data.remote.dto.location.LocationDto
import com.example.geekstudiorickmorty.data.remote.dto.location.LocationResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickyAndMortyApi {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("status") status: String="",
        @Query("gender") gender: String="",
        @Query("name") name: String="",
        @Query("page") page: Int? = null
    ): CharactersDto


    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterData

}