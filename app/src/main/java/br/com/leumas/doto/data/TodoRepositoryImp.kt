package br.com.leumas.doto.data

import android.content.Context
import android.util.Log
import br.com.leumas.doto.ui.models.Todo
import javax.inject.Inject

class TodoRepositoryImp @Inject constructor(
    val context: Context,
    val message: String
): TodoRepository {
    override fun getTodos() {
        Log.d("TodoRepository", "getTodos called")
    }

    override fun saveTodo(todo: Todo) {
        Log.d("TodoRepository", "saveTodo called")
        Log.d("TodoRepository", message)
    }
}
