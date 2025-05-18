package com.example.jetpack_compose_assignment_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jetpack_compose_assignment_2.ui.theme.JetpackComposeAssignment2Theme
import com.example.jetpack_compose_assignment_2.ui.navigation.TodoNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAssignment2Theme {
                TodoNavHost()
            }
        }
    }
}
