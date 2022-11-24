package com.example.date_time_dialogs__compose.di

import com.example.date_time_dialogs__compose.RetrofitUploadActivity
import com.example.date_time_dialogs__compose.retrofit_up_act.FileRepository
import com.example.date_time_dialogs__compose.retrofit_up_act.FileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { FileRepository() }
    viewModel { FileViewModel(repository = get(),context = get()) }

    scope<RetrofitUploadActivity> {

    }
}