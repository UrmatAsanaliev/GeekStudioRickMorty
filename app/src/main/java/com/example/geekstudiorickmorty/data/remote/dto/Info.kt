package com.example.geekstudiorickmorty.data.remote.dto

data class Info(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?
)