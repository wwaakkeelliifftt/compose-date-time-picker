package com.example.date_time_dialogs__compose.connection_check

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Connecting, Losing, Lost
    }
}