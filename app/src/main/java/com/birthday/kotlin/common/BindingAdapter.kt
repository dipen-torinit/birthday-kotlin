package com.birthday.kotlin.common

import android.util.Base64
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.birthday.kotlin.R
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("bSetImageBitmap")
    fun loadImage(iv: ImageView, base64: String?) {
        Glide.with(iv.context)
            .load(Base64.decode(base64, Base64.DEFAULT))
            .placeholder(R.drawable.ic_app_icon)
            .error(R.drawable.ic_app_icon)
            .into(iv)
    }
}