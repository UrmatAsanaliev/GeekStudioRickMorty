package com.example.geekstudiorickmorty.data.remote.dto.character

import com.example.geekstudiorickmorty.domain.model.CharacterDomain

data class CharacterDto(
    val result: CharacterData
)


fun CharacterDto.toCharacter(): CharacterDomain {
    return CharacterDomain(
        result = result
    )
}