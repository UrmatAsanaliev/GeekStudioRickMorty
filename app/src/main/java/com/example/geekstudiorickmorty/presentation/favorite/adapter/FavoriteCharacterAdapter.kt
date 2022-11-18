package com.example.geekstudiorickmorty.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.geekstudiorickmorty.R
import com.example.geekstudiorickmorty.databinding.CharacterItemFavListBinding
import com.example.geekstudiorickmorty.domain.model.CharactersDomain
import com.example.geekstudiorickmorty.presentation.character.view.CharacterListFragmentDirections
import com.example.geekstudiorickmorty.presentation.favorite.view.FavoriteListFragmentDirections
import comprmto.rickyandmorty.presentation.adapter.DiffUtilCallBack
import comprmto.rickyandmorty.presentation.adapter.FROMCHARACTERLIST
import comprmto.rickyandmorty.presentation.adapter.FROMFAVORITELIST

class FavoriteCharacterAdapter :
    ListAdapter<CharactersDomain, FavoriteCharacterAdapter.CharacterViewHolder>(DiffUtilCallBack()) {


    class CharacterViewHolder(val binding: CharacterItemFavListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.characterModel?.id?.let { id ->
                    navigateToCharacterDetail(id, it, FROMFAVORITELIST)
                }
            }

        }

        fun navigateToCharacterDetail(id: Int, view: View, whichFragment: String) {

            val direction = if (FROMCHARACTERLIST == whichFragment) {
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    id
                )
            } else {
                FavoriteListFragmentDirections.actionFavoriteListFragmentToCharacterDetailFragment(
                    id
                )
            }

            view.findNavController().navigate(direction)
        }

        companion object {
            fun create(parent: ViewGroup): CharacterViewHolder {
                val binding = CharacterItemFavListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CharacterViewHolder(binding)
            }
        }

        fun bind(charactersDomain: CharactersDomain) {
            binding.characterModel = charactersDomain
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val charactersDomain = getItem(position)
        holder.bind(charactersDomain)

        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.up_anim
        )
    }
}

