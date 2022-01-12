package com.birthday.kotlin.ui

import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutID: Int) : Fragment(layoutID) {
    fun setFullScreen(showFullScreen: Boolean) {
        (requireActivity() as LauncherActivity).setFullScreen(showFullScreen)
    }
}