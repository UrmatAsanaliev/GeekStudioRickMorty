package com.example.geekstudiorickmorty.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Characters(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val image: String,
    val species: String,
    var isFavorite: Boolean = false
) {
    fun setFavoriteState(newState: Boolean) {
        this.isFavorite = newState
    }
}