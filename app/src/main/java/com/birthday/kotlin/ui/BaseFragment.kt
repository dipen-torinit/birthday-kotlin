package com.birthday.kotlin.ui

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
abstract class BaseFragment(layoutID: Int) : Fragment(layoutID) {
    fun setFullScreen(showFullScreen: Boolean) {
        (requireActivity() as LauncherActivity).setFullScreen(showFullScreen)
    }

    fun showProgress(showProgress: Boolean) {
        (requireActivity() as LauncherActivity).run {
            if (showProgress) showProgress() else hideProgress()
        }
    }

    /*
    * Setup View, Listener etc.
    * */
    abstract fun setupView()

    /*
    * Initialize LiveData observer or Flow collector
    * */
    abstract fun setupCollector()

    /*
    * Execute all the calls that we want to execute when fragment is launched.
    * This function needs to be called from "onResume" method...if we call it from "onViewCreated" then Flow is unable to collect the data
    * */
    abstract fun executeCallsWhenResume()

    fun <K> collectFlow(flow: SharedFlow<K>, function: (K) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            flow.collect {
                function.invoke(it)
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}