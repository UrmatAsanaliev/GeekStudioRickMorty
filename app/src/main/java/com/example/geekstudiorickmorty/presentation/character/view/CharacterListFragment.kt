package com.example.geekstudiorickmorty.presentation.character.view


import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geekstudiorickmorty.R
import com.example.geekstudiorickmorty.core.BaseFragment
import com.example.geekstudiorickmorty.databinding.FragmentCharacterListBinding
import com.example.geekstudiorickmorty.domain.model.Characters
import com.example.geekstudiorickmorty.presentation.character.viewmodel.CharacterViewModel
import com.example.geekstudiorickmorty.presentation.character.viewmodel.states.ListType
import com.example.geekstudiorickmorty.presentation.favorite.adapter.FavoriteCharacterAdapter
import com.example.geekstudiorickmorty.presentation.favorite.viewModel.FavoriteViewModel
import com.example.geekstudiorickmorty.util.ItemLongClickListener
import com.example.geekstudiorickmorty.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : BaseFragment<FragmentCharacterListBinding>
    (R.layout.fragment_character_list) {

    lateinit var viewModel: CharacterViewModel

    private lateinit var viewModelFavorite: FavoriteViewModel

    private lateinit var characterAdapter: CharacterAdapter

    private lateinit var favoriteAdapter: FavoriteCharacterAdapter

    override fun setupObserver() {
        toSortFragment()
        btnRefresh()
    }

    private fun toSortFragment() {
        binding.imageButton.setOnClickListener {
            val action =
                CharacterListFragmentDirections.actionCharacterListFragmentToFilterDialog()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun setupUi() {
        initViewModel()
        initAdapter()
        prepareCharacterAdapter()
        setCharacterListLayoutManager()
        getListData()
        initRefresh()
        isNetworkAvailable(requireContext())
    }


    override fun noInternet() {
        noInternets()
    }

    override fun internetConnected() {
        binding.refreshBtn.visibility = View.GONE
        initAdapter()
        setCharacterListLayoutManager()
        prepareCharacterAdapter()
        getListData()
    }

    private fun noInternets() {
        Toast.makeText(context, "У вас нету интернета!1!!", Toast.LENGTH_LONG).show()
        favoriteAdapter = FavoriteCharacterAdapter()
        binding.characterList.adapter = favoriteAdapter
        binding.characterList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initRefresh() {
        lifecycleScope.launch {
            characterAdapter.loadStateFlow.collect {
                val isListEmpty = it.refresh is LoadState.Error && characterAdapter.itemCount == 0
                Util.loadingState(
                    it,
                    binding.refreshBtn,
                    isListEmpty,
                    viewModel.checkIfTheFilterHasBeenApplied()
                )
            }
        }
    }

    private fun btnRefresh() {
        binding.refreshBtn.setOnClickListener {
            isNetworkAvailable(requireContext())
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity())[CharacterViewModel::class.java]

        viewModelFavorite = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.checkIfTheFilterHasBeenApplied()
    }

    private fun initAdapter() {
        favoriteAdapter = FavoriteCharacterAdapter()
        characterAdapter = CharacterAdapter(
            ItemLongClickListener {
                showAlertDialog(it)
            },
        )


        lifecycleScope.launch {
            viewModelFavorite.getFavoriteCharacters().collect {
                favoriteAdapter.submitList(it)
            }
        }
    }


    private fun setCharacterListLayoutManager() {
        binding.characterList.layoutManager = GridLayoutManager(requireContext(), 2)
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


    override fun onPause() {
        super.onPause()
        characterAdapter.snapshot().items.forEach { viewModel.insertMyFavoriteList(it) }
    }


}