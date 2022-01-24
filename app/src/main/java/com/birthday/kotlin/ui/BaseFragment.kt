package com.birthday.kotlin.ui

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
abstract class BaseFragment(layoutID: Int) : Fragment(layoutID) {
    fun setFullScreen(showFullScreen: Boolean) {
        (requireActivity() as LauncherActivity).setFullScreen(showFullScreen)
    }

    /*
    * Setup View, Listener etc.
    * */
    abstract fun setupViews()

    /*
    * Initialize LiveData observer or Flow collector
    * */
    abstract fun setupCollectors()

    /*
    * Execute all the calls that we want to execute when fragment is launched.
    * Collector should be set before data gets emit otherwise it wont be collected and UI wont get updated.
    * So we can "setupCollector" on "onViewCreated" method and "executeCallsWhenResume" on "onResume" so that collector gets set before data gets emit.
    * In can of network call it wont matter much if you call "setupCollector" just after/before "executeCallsWhenResume" as data will emitted after sometime but in synchronous call it will cause issue.
    *
    * Other solution is that we can set replay value while initializing SharedFlow so that whenever new observer get register it will send the last collected value to that collector.
    * private val _personsList = MutableSharedFlow<Result<List<Person>>>(1)
    * */
    abstract fun executeCallsWhenResume()

    fun <K> collectFlow(flow: SharedFlow<K>, function: (K) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            flow.collect {
                function.invoke(it)
            }
        }
    }

    fun <K> collectFlow(flow: StateFlow<K>, function: (K) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            flow.collect {
                function.invoke(it)
            }
        }
    }

    fun <K> collectFlow(flow: Flow<K>, function: (K) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            flow.collect {
                function.invoke(it)
            }
        }
    }

    fun showMessage(view: View, @StringRes message: Int) =
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

    fun showMessage(view: View, message: String) =
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}