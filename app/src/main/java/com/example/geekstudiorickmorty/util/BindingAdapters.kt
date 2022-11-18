package com.example.geekstudiorickmorty.util

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.geekstudiorickmorty.R

@BindingAdapter("imageUrl")
fun downloadImage(imageView: ImageView, url: String?) {

    url?.let {
        imageView.load(url) {
            crossfade(true)
                .error(R.drawable.error_image)
                .placeholder(R.drawable.animate_loading)
        }

    }
}



@BindingAdapter("isVisible")
fun isFilter(view: View, isFilter: Boolean) {
    view.visibility = if (isFilter) View.VISIBLE else View.GONE
}

@BindingAdapter("isFilter")
fun isFilter(textView: TextView, checkIsFilter: () -> Boolean) {

    textView.visibility = if (checkIsFilter.invoke()) View.VISIBLE else View.GONE
}

@BindingAdapter("isLoading")
fun isLoading(progressBar: ProgressBar, isLoading: Boolean) {

    if (isLoading) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }
}


// We determine the color according to the status of the characters.
@BindingAdapter("statusColor")
fun changeColor(card: CardView, status: String) {

    if (status == "Dead") {
        card.setCardBackgroundColor(Color.RED)
    } else if (status == "Alive") {
        card.setCardBackgroundColor(Color.GREEN)
    } else {
        card.setCardBackgroundColor(Color.GRAY)
    }
}

@BindingAdapter("statusColor")
fun changeColor(textView: TextView, status: String) {

    if (status == "Dead") {
        textView.setTextColor(Color.RED)
    } else if (status == "Alive") {
        textView.setTextColor(Color.GREEN)
    } else {
        textView.setTextColor(Color.GRAY)
    }
}


