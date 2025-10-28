package com.thoughtworks.moments.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.thoughtworks.moments.data.remote.repository.MomentRepository
import com.thoughtworks.moments.ui.screen.theme.TheWeChatMomentsTheme
import com.thoughtworks.moments.ui.viewmodels.MainViewModel
import com.thoughtworks.moments.ui.viewmodels.MainViewModelFactory

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