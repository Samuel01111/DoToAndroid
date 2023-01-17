package br.com.leumas.doto.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leumas.doto.R
import br.com.leumas.doto.data.repository.TodoRepository
import br.com.leumas.doto.ui.models.Todo
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
    ) : ViewModel() {

    sealed class FieldState {
        object ValidFields: FieldState()
        class InvalidFields(val fields: List<Pair<String, Int>>): FieldState()
    }

    var listOfTodo: MutableList<Todo> = mutableListOf()

    private val _fieldsStateEvent = MutableLiveData<FieldState>()
    val fieldsStateEvent: LiveData<FieldState>
        get() = _fieldsStateEvent

    private val _onDatabaseDataEvent = MutableLiveData<List<Todo>>()
    val onDatabaseDataEvent: LiveData<List<Todo>>
        get() = _onDatabaseDataEvent

    init {
        getAllTodo()
        //getListOfTodoMocked()
    }

    fun addTodoIntoList(todo: Todo) {
        //this call is viewmodel scoped
        viewModelScope.launch {
            repository.saveTodo(todo)
        }
    }

    private fun getAllTodo(){
        viewModelScope.launch {
           listOfTodo = repository.getTodos().toMutableList()
            _onDatabaseDataEvent.postValue(listOfTodo)
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

    /*private fun getListOfTodoMocked() {
        listOfTodo = mutableListOf(
            Todo(
                title = "VAMO TREINA",
                description = "não esquece do pré treino em mano tem uma par de coisa pra nós pega lá po",
                isFavorite = true,
                isCompleted = false,
                createDate = "08/10/2023"
            ),
            Todo(
                title = "VAMO TREINA",
                description = "não esquece do pré treino em mano",
                isFavorite = true,
                isCompleted = false,
                createDate = "08/10/2023"
            ),
            Todo(
                title = "VAMO TREINA",
                description = "não esquece do pré treino em mano, inclusive a agua tbm",
                isFavorite = true,
                isCompleted = false,
                createDate = "08/10/2023"
            ),
            Todo(
                title = "VAMO TREINA",
                description = "não esquece do pré ",
                isFavorite = true,
                isCompleted = false,
                createDate = "08/10/2023"
            ),
            Todo(
                title = "VAMO TREINA",
                description = "não esquece do pré treino em mano",
                isFavorite = true,
                isCompleted = false,
                createDate = "08/10/2023"
            )
        )
    } */

    companion object {
        val INPUT_TITLE = "INPUT_TITLE" to R.string.error_todo_title
        val INPUT_DESCRIPTION = "INPUT_DESCRIPTION" to R.string.error_todo_description
    }
}