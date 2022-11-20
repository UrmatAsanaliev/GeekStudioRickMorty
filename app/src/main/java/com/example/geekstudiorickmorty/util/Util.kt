package com.example.geekstudiorickmorty.util

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.airbnb.lottie.LottieAnimationView

class Util {

    companion object {
        fun loadingState(
            state: CombinedLoadStates,
            button: Button,
            isListEmpty: Boolean = false,
            isFilterHasBeenApplied: Boolean = false
        ) {

            if (state.source.refresh is LoadState.Loading) {
                button.visibility = View.GONE
            } else if ((state.source.refresh is LoadState.Error || state.source.append is LoadState.Error) && !isFilterHasBeenApplied) {
                button.visibility = View.VISIBLE
            } else {
                button.visibility = View.GONE
            }
        }
    }
}