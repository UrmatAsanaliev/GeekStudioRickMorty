package com.example.geekstudiorickmorty.presentation.character.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.geekstudiorickmorty.R
import com.example.geekstudiorickmorty.data.remote.dto.character.toCharactersDomain
import com.example.geekstudiorickmorty.domain.model.Characters
import com.example.geekstudiorickmorty.domain.use_case.DeleteCharacterFromMyFavoriteListUseCase
import com.example.geekstudiorickmorty.domain.use_case.GetAllCharactersUseCase
import com.example.geekstudiorickmorty.domain.use_case.GetAllFavoriteCharactersUseCase
import com.example.geekstudiorickmorty.domain.use_case.InsertMyFavoriteListUseCase
import com.example.geekstudiorickmorty.presentation.character.viewmodel.states.CharacterActivityState
import com.example.geekstudiorickmorty.util.GenderState
import com.example.geekstudiorickmorty.util.StatusState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val insertMyFavoriteListUseCase: InsertMyFavoriteListUseCase,
    private val getAllFavoriteCharactersUseCase: GetAllFavoriteCharactersUseCase,
    private val deleteCharacterFromMyFavoriteListUseCase: DeleteCharacterFromMyFavoriteListUseCase,
    private val app: Application
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterActivityState())
    val state: StateFlow<CharacterActivityState> get() = _state

    init {
        getAllFavoriteCharacters()

        viewModelScope.launch {
            getListData().collect {
                _state.value = _state.value.copy(
                    characters = it
                )
            }
        }

        _state.value.favoriteCharacter.forEach {
            Timber.d(it.name)
        }
    }

    suspend fun getListData(): Flow<PagingData<Characters>> {
        var characterName = _state.value.queryCharacterName.value

        if (characterName == null) {
            characterName = ""
        }

        val list = _state.value.favoriteCharacter

        Log.e("-------list", "${list.size}")

        return getAllCharactersUseCase.getAllCharacters(
            status = _state.value.statusState,
            gender = _state.value.genderState,
            characterName
        ).toCharactersDomain(list)
    }

    fun setStatusState(status: StatusState) {
        _state.value = _state.value.copy(
            statusState = status
        )
    }

    fun setGenderState(genderState: GenderState) {
        _state.value = _state.value.copy(
            genderState = genderState
        )
    }

    fun checkIfTheFilterHasBeenApplied(): Boolean {

        val statusValue = _state.value.statusState
        val genderValue = _state.value.genderState
        val characterName = _state.value.queryCharacterName

        if (statusValue == StatusState.NONE && genderValue == GenderState.NONE && characterName.value == "") {
            _state.value = _state.value.copy(
                isFilter = false
            )
        } else {
            _state.value = _state.value.copy(
                isFilter = true
            )
        }

        return _state.value.isFilter
    }


    fun insertMyFavoriteList(character: Characters) {
        viewModelScope.launch {
            try {
                insertMyFavoriteListUseCase.insertMyFavoriteCharacters(character)
                updateToastMessage(app.getString(R.string.toast_message_success))
            } catch (e: Exception) {
                updateToastMessage(app.getString(R.string.toast_message_error))
            }
        }
        updateToastState()
    }

    fun getAllFavoriteCharacters() {
        viewModelScope.launch {
            getAllFavoriteCharactersUseCase.getAllFavoriteCharacters().collect {
                _state.value = _state.value.copy(
                    favoriteCharacter = it
                )
            }
        }


    }

    fun deleteCharacterFromMyFavoriteList(character: Characters) {
        viewModelScope.launch {
            try {
                deleteCharacterFromMyFavoriteListUseCase.deleteCharacterFromMyFavoriteList(character)
                updateToastMessage(app.getString(R.string.toast_message_success))
            } catch (e: Exception) {
                updateToastMessage(app.getString(R.string.toast_message_error))
            }
        }
        updateToastState()
    }

    private fun updateToastMessage(message: String) {
        _state.value = _state.value.copy(
            toastMessage = message
        )
    }

    private fun updateToastState() {
        _state.value = _state.value.copy(
            showToastMessageEvent = true
        )
    }

    fun doneToastMessage() {

        _state.value = _state.value.copy(
            showToastMessageEvent = false,
            toastMessage = ""
        )
    }

    private fun getFavoriteCharacter(): List<Characters> {
        return _state.value.favoriteCharacter
    }

    fun isHasAddedCharacter(charactersDomain: Characters): Boolean {

        val myFavoriteList = this.getFavoriteCharacter()
        var result = false

        myFavoriteList.forEach {
            if (it.id == charactersDomain.id) {
                result = true
            }
        }

        return result
    }

    fun getIsShowToastMessage(): Boolean {
        return _state.value.showToastMessageEvent
    }

    fun getToastMessage(): String {
        return _state.value.toastMessage
    }
}