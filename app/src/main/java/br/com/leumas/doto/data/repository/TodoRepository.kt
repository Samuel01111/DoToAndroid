package br.com.leumas.doto.data.repository

import br.com.leumas.doto.ui.models.Todo

interface TodoRepository {

    suspend fun saveTodo(todo: Todo)

    suspend fun getTodos(): List<Todo>
}
