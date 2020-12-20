package com.dicoding.naufal.footballmatchschedule.utils

import androidx.core.content.ContextCompat
import android.view.ViewManager
import com.dicoding.naufal.footballmatchschedule.R.color.colorWhite
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar


fun ViewManager.myToolbar() = toolbar {
    lparams(width = matchParent)
    setTitleTextColor(ContextCompat.getColor(context, colorWhite))
}


fun measurementOrNull(s: String?): String? {
    return if (s.isNullOrEmpty() || s == "0")
        "N/A"
    else
        s
}
