package com.example.geekstudiorickmorty.data.remote.dto.episode

import com.example.geekstudiorickmorty.data.remote.dto.Info
import com.example.geekstudiorickmorty.domain.model.EpisodeDomain

data class EpisodeDto(
    val info: Info,
    val results: List<EpisodeResult>
)

fun EpisodeDto.toEpisodeDomain(): List<EpisodeDomain> {

    return results.map {
        EpisodeDomain(
            id = it.id,
            name = it.name,
            air_date = it.air_date,
            episode = it.episode,
            characters = it.characters
        )
    }
}