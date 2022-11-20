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


