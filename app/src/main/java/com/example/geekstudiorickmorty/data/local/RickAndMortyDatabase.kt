package com.example.geekstudiorickmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.geekstudiorickmorty.domain.model.Characters

@Database(entities = [Characters::class], version = 1)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract val rickMortyDao: RickAndMortyDao
}