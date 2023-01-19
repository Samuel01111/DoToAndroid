package br.com.leumas.doto.ui.edit

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leumas.doto.R
import br.com.leumas.doto.data.db.TodoEntity
import br.com.leumas.doto.data.repository.TodoRepository
import br.com.leumas.doto.ui.models.Todo
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditTodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _fieldsStateEvent = MutableLiveData<FieldState>()
    val fieldsStateEvent: LiveData<FieldState>
        get() = _fieldsStateEvent

    val currentTodo = ObservableField<Todo>()

    fun fetchTodoById(id: Int) {
        viewModelScope.launch {
            currentTodo.set(repository.getTodoById(id))
        }
    }

    fun updateTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun removeTodoById(id: Int) {
        viewModelScope.launch {
            repository.removeTodoById(id)
        }
    }

    fun isValidForm(title: String, description: String): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if (title.isEmpty()) {
            invalidFields.add(INPUT_TITLE)
        }

        if (description.isEmpty()) {
            invalidFields.add(INPUT_DESCRIPTION)
        }

        if (invalidFields.isNotEmpty()) {
            _fieldsStateEvent.postValue(FieldState.InvalidFields(invalidFields))
            return false
        }
        _fieldsStateEvent.postValue(FieldState.ValidFields)
        return true
    }

    sealed class FieldState {
        object ValidFields: FieldState()
        class InvalidFields(val fields: List<Pair<String, Int>>): FieldState()
    }

    companion object {
        val INPUT_TITLE = "INPUT_TITLE" to R.string.error_todo_title
        val INPUT_DESCRIPTION = "INPUT_DESCRIPTION" to R.string.error_todo_description
    }
}
