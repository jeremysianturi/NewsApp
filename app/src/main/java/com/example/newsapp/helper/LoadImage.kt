package com.example.newsapp.helper

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.newsapp.R

fun ImageView.loadImage(
    context: Context,
    url: String,
    @DrawableRes placeholder: Int = R.drawable.ic_image_search
) {

    // add loading image
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 50f
    circularProgressDrawable.setColorSchemeColors(
        ContextCompat.getColor(context, R.color.orange_500)
    )
    circularProgressDrawable.start()

    Glide.with(this)
        .load(url)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_image_search)
        .into(this)

}