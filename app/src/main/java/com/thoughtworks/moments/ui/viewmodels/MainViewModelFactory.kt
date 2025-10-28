package com.thoughtworks.moments.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thoughtworks.moments.data.remote.repository.MomentRepository

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