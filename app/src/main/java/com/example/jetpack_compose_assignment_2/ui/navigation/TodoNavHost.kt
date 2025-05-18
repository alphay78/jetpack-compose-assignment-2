package com.example.jetpack_compose_assignment_2.ui.navigation

import TodoDetailViewModelFactory
import TodoListViewModelFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpack_compose_assignment_2.data.local.TodoDatabase
import com.example.jetpack_compose_assignment_2.data.remote.RetrofitInstance
import com.example.jetpack_compose_assignment_2.repository.TodoRepository
import com.example.jetpack_compose_assignment_2.ui.screens.TodoDetailScreen
import com.example.jetpack_compose_assignment_2.ui.screens.TodoListScreen
import com.example.jetpack_compose_assignment_2.viewmodel.TodoDetailViewModel
import com.example.jetpack_compose_assignment_2.viewmodel.TodoListViewModel

// TodoNavHost.kt
@Composable
fun TodoNavHost() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val repository = remember {
        TodoRepository(
            api = RetrofitInstance.api,
            dao = TodoDatabase.getDatabase(context).todoDao()
        )
    }

    val listViewModelFactory = TodoListViewModelFactory(repository)
    val detailViewModelFactory = TodoDetailViewModelFactory(repository)

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            val viewModel: TodoListViewModel = viewModel(factory = listViewModelFactory)
            TodoListScreen(viewModel, navController)
        }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            val viewModel: TodoDetailViewModel = viewModel(factory = detailViewModelFactory)
            viewModel.loadTodo(id)
            TodoDetailScreen(viewModel, navController)
        }
    }
}


