package br.com.leumas.doto.data.repository

import android.content.Context
import android.util.Log
import br.com.leumas.doto.data.db.TodoEntity
import br.com.leumas.doto.data.db.dao.TodoDao
import br.com.leumas.doto.data.db.toTodo
import br.com.leumas.doto.data.db.toTodoEntity
import br.com.leumas.doto.data.db.toTodoList
import br.com.leumas.doto.ui.models.Todo
import javax.inject.Inject

class TodoDbDataSource @Inject constructor(
    val context: Context,
    private val todoDao: TodoDao,
    private val message: String
): TodoRepository {

    override suspend fun saveTodo(todo: Todo) {
        val todoEntity = todo.toTodoEntity()
        todoDao.saveTodo(todoEntity)
        Log.d("TodoRepository", message)
    }

    override suspend fun updateTodo(todo: TodoEntity) {
        todoDao.saveTodo(todo)
    }

    override suspend fun getTodos(): List<Todo> {
        return todoDao.getTodos().toTodoList()
    }

    override suspend fun getTodoById(id: Int): Todo {
        return todoDao.getTodoById(id).toTodo()
    }
}
