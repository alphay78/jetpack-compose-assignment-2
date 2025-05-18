package com.example.jetpack_compose_assignment_2.repository

import com.example.jetpack_compose_assignment_2.data.local.TodoDao
import com.example.jetpack_compose_assignment_2.data.local.TodoEntity
import com.example.jetpack_compose_assignment_2.data.remote.TodoApi
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val api: TodoApi,
    private val dao: TodoDao
) {
    fun getCachedTodos(): Flow<List<TodoEntity>> = dao.getAllTodos()

    suspend fun fetchTodosFromNetworkAndCache() {
        try {
            val todos = api.getTodos()
            val entities = todos.map {
                TodoEntity(
                    id = it.id,
                    userId = it.userId,
                    title = it.title,
                    completed = it.completed
                )
            }
            dao.insertTodos(entities)
        } catch (e: Exception) {
            // Handle error silently for now
        }
    }

    suspend fun getTodoById(id: Int): TodoEntity? = dao.getTodoById(id)
}