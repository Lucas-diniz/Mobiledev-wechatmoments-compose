package com.thoughtworks.moments.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thoughtworks.moments.api.MomentRepository

class MainViewModelFactory(
    private val repository: MomentRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass == MainViewModel::class.java) {
            MainViewModel(repository) as T
        } else {
            super.create(modelClass)
        }
    }
}