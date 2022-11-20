package com.example.geekstudiorickmorty.util

import com.example.geekstudiorickmorty.domain.model.Characters

class ItemClickListener(val clickListener: (id: Int) -> Unit) {
    fun onClick(id: Int) = clickListener(id)
}

class ItemLongClickListener(val longClickListener: (character: Characters) -> Unit) {
    fun onLongClick(character: Characters) = longClickListener(character)
}