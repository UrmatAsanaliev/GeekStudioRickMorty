package com.example.geekstudiorickmorty.presentation.character.viewmodel.states

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.geekstudiorickmorty.domain.model.Characters
import com.example.geekstudiorickmorty.util.GenderState
import com.example.geekstudiorickmorty.util.StatusState

data class CharacterActivityState(
    val characters: PagingData<Characters>? = PagingData.empty(),
    val characterIdFromCharacterListFragment: Int = 1,
    val statusState: StatusState = StatusState.NONE,
    val genderState: GenderState = GenderState.NONE,
    val queryCharacterName: MutableLiveData<String> = MutableLiveData(""),
    val isFilter: Boolean = false,
    val favoriteCharacter: List<Characters> = emptyList(),
    val showToastMessageEvent: Boolean = false,
    val toastMessage: String = "",
    val listType: ListType = ListType.GridLayout

)

enum class ListType() {
    LinearLayout(),
    GridLayout()
}


