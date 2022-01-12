package com.birthday.kotlin.ui

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(layoutID: Int) : Fragment(layoutID) {
    fun setFullScreen(showFullScreen: Boolean) {
        (requireActivity() as LauncherActivity).setFullScreen(showFullScreen)
    }
}