package com.example.geekstudiorickmorty.presentation.character.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geekstudiorickmorty.databinding.FragmentCharacterListBinding
import com.example.geekstudiorickmorty.domain.model.Characters
import com.example.geekstudiorickmorty.presentation.character.viewmodel.CharacterViewModel
import com.example.geekstudiorickmorty.presentation.character.viewmodel.states.ListType
import com.example.geekstudiorickmorty.presentation.favorite.viewModel.FavoriteViewModel
import com.example.geekstudiorickmorty.util.CalculateWindowSize
import com.example.geekstudiorickmorty.util.ItemLongClickListener
import com.example.geekstudiorickmorty.util.Util
import com.example.geekstudiorickmorty.util.WindowSizeClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding
    lateinit var viewModel: CharacterViewModel
    lateinit var viewModelFavorite: FavoriteViewModel
    private lateinit var characterAdapter: CharacterAdapter
    lateinit var widthWindowClass: WindowSizeClass


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentCharacterListBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[CharacterViewModel::class.java]
        viewModelFavorite = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]

        widthWindowClass = CalculateWindowSize(requireActivity()).calculateCurrentWidthSize()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        prepareCharacterAdapter()
        setCharacterListLayoutManager()

        viewModel.checkIfTheFilterHasBeenApplied()

        getListData()

        binding.refreshBtn.setOnClickListener {
        }

        lifecycleScope.launch {
            characterAdapter.loadStateFlow.collect {
                val isListEmpty =
                    it.refresh is LoadState.Error && characterAdapter.itemCount == 0
                Util.loadingState(
                    it,
                    binding.refreshBtn,
                    isListEmpty,
                    viewModel.checkIfTheFilterHasBeenApplied()
                )
            }
        }

        return binding.root
    }

    private fun setCharacterListLayoutManager() {
            val spanCount = if (widthWindowClass == WindowSizeClass.EXPANDED) 3 else 2
            binding.characterList.layoutManager = GridLayoutManager(requireContext(), spanCount)
            characterAdapter.setListType(ListType.GridLayout)

    }

    private fun getListData() {
        lifecycleScope.launch {
            viewModel.getListData().collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

    private fun prepareCharacterAdapter() {
        characterAdapter = CharacterAdapter(
            ItemLongClickListener {
                showAlertDialog(it)
            }
        )
        setCharacterListLayoutManager()
        characterAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.characterList.adapter = characterAdapter

    }

    private fun showAlertDialog(charactersDomain: Characters) {

        viewModel.getAllFavoriteCharacters()

        val isHasAddedCharacter = viewModel.isHasAddedCharacter(charactersDomain)

            if (isHasAddedCharacter) {
                viewModel.deleteCharacterFromMyFavoriteList(charactersDomain)

            } else {
                charactersDomain.setFavoriteState(true)
                viewModel.insertMyFavoriteList(charactersDomain)
            }
            showToastMessage()
            viewModel.doneToastMessage()

    }

    private fun showToastMessage() {
        if (viewModel.getIsShowToastMessage()) {
            Toast.makeText(
                requireContext(),
                viewModel.getToastMessage(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButton.setOnClickListener {
            val action =
                CharacterListFragmentDirections.actionCharacterListFragmentToFilterDialog()
            Navigation.findNavController(it).navigate(action)
        }
    }
}