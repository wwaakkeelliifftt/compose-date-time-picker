package com.example.date_time_dialogs__compose.retrofit_up_act

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.date_time_dialogs__compose.connection_check.ConnectivityObserver
import com.example.date_time_dialogs__compose.connection_check.NetworkConnectivityObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import kotlin.reflect.KProperty


class FileViewModel(
    private val repository: FileRepository,
    context: Context
): ViewModel() {

    private val connectivityObserver = NetworkConnectivityObserver(context)
    var networkState by mutableStateOf(ConnectivityObserver.Status.Unavailable)
        private set
    var isChanged by mutableStateOf(false)

    init {
        viewModelScope.launch {
            connectivityObserver.observe().collect {
                networkState = it
                isChanged = !isChanged
//                executeColorFlash()
            }
        }
    }

    private suspend fun executeColorFlash() {
        isChanged = true
        delay(1000)
        isChanged = false
    }

    var isUploaded by mutableStateOf(false)
        private set

    private fun setIsUploaded(newValue: Boolean) {
        isUploaded = newValue
    }

    fun uploadImage(file: File) {
        setIsUploaded(true)
        viewModelScope.launch {
            val result = repository.uploadImage(file = file)
            delay(3000)
            setIsUploaded(result)
        }
    }

}