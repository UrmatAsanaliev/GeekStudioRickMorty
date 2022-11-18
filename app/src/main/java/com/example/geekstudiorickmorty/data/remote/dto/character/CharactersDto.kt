package com.example.geekstudiorickmorty.data.remote.dto.character

import com.example.geekstudiorickmorty.data.remote.dto.Info


data class CharactersDto(
    val info: Info,
    val results: List<CharacterData>
)

