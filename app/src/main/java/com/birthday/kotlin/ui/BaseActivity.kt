package com.birthday.kotlin.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    fun <K> collectFlow(flow: SharedFlow<K>, function: (K) -> Unit) {
        this.lifecycleScope.launchWhenStarted {
            flow.collect {
                function.invoke(it)
            }
        }
    }
}