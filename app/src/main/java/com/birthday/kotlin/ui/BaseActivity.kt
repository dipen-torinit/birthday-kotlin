package com.birthday.kotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.birthday.kotlin.common.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)
    }

    fun <K> collectFlow(flow: SharedFlow<K>, function: (K) -> Unit) {
        this.lifecycleScope.launchWhenStarted {
            flow.collect {
                function.invoke(it)
            }
        }
    }
}