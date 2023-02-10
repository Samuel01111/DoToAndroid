package br.com.leumas.doto.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leumas.doto.R
import br.com.leumas.doto.data.repository.TodoRepository
import br.com.leumas.doto.ui.models.Todo
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTodoViewModel @Inject constructor(
    private val repository: TodoRepository
    ): ViewModel() {

    private val _fieldsStateEvent = MutableLiveData<FieldState>()
    val fieldsStateEvent: LiveData<FieldState>
        get() = _fieldsStateEvent

    fun saveTodo(todo: Todo) {
        viewModelScope.launch {
            repository.saveTodo(todo)
        }
    }

    fun isValidForm(title: String, description: String): Boolean {
        val invalidFields = arrayListOf<Pair<String, Int>>()
        if (title.isEmpty()) {
            invalidFields.add(INPUT_TITLE)
        }

        if (title.length > 20) {
            invalidFields.add(INPUT_TITLE_LARGE)
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
        val INPUT_TITLE_LARGE = "INPUT_TITLE_LARGE" to R.string.error_todo_title_too_large
        val INPUT_DESCRIPTION = "INPUT_DESCRIPTION" to R.string.error_todo_description
    }
}
