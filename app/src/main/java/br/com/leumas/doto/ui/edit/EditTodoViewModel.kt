package br.com.leumas.doto.ui.edit

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leumas.doto.data.repository.TodoRepository
import br.com.leumas.doto.ui.models.Todo
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditTodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val currentTodo = ObservableField<Todo>()

    fun fetchTodoById(id: Int) {
        viewModelScope.launch {
            currentTodo.set(repository.getTodoById(id))
        }
    }

    fun saveTodo() {
        viewModelScope.launch {
            currentTodo.get()?.let {
                repository.saveTodo(it)
            }
        }
    }
}
