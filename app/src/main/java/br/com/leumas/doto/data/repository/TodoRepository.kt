package br.com.leumas.doto.data.repository

import br.com.leumas.doto.data.db.TodoEntity
import br.com.leumas.doto.ui.models.Todo

interface TodoRepository {

    suspend fun saveTodo(todo: Todo)

    suspend fun updateTodo(todo: TodoEntity)

    suspend fun getTodos(): List<Todo>

    suspend fun getTodoById(id: Int): Todo
}
