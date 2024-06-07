package com.thoughtworks.moments.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thoughtworks.moments.api.MomentRepository

class MainViewModelFactory(
  private val repository: MomentRepository
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass == MainViewModel::class.java) {
      return MainViewModel(repository) as T
    } else {
      return super.create(modelClass)
    }
  }
}