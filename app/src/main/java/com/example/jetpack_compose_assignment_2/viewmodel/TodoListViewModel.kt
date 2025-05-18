package com.example.jetpack_compose_assignment_2.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_compose_assignment_2.data.local.TodoEntity
import com.example.jetpack_compose_assignment_2.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoRepository) : ViewModel() {
    private val _todos = MutableStateFlow<List<TodoEntity>>(emptyList())
    val todos: StateFlow<List<TodoEntity>> = _todos

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            repository.getCachedTodos().collectLatest {
                _todos.value = it
            }
        }
        viewModelScope.launch {
            repository.fetchTodosFromNetworkAndCache()
        }
    }
}

