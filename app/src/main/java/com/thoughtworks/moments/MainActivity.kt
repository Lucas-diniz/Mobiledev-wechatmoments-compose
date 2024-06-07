package com.thoughtworks.moments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.thoughtworks.moments.api.MomentRepository
import com.thoughtworks.moments.screen.MainScreen
import com.thoughtworks.moments.ui.theme.TheWeChatMomentsTheme
import com.thoughtworks.moments.viewmodels.MainViewModel
import com.thoughtworks.moments.viewmodels.MainViewModelFactory

class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels {
    val repository = MomentRepository()
    MainViewModelFactory(repository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      TheWeChatMomentsTheme {
        MainScreen(viewModel)
      }
    }
  }
}