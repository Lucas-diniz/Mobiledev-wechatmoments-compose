package com.thoughtworks.moments.di

import com.thoughtworks.moments.domain.repository.MomentRepository
import com.thoughtworks.moments.ui.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiDi = module {
    viewModel { MainViewModel(get<MomentRepository>()) }
}