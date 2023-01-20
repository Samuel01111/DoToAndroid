package br.com.leumas.doto.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.leumas.doto.data.db.TodoEntity
import br.com.leumas.doto.data.repository.TodoRepository
import br.com.leumas.doto.ui.models.Todo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
    ) : ViewModel() {

    var listOfTodo: MutableList<Todo> = mutableListOf()

    private val _onDatabaseDataEvent = MutableLiveData<List<Todo>?>(null)
    val onDatabaseDataEvent: LiveData<List<Todo>?>
        get() = _onDatabaseDataEvent


    fun getAllTodo() {
        viewModelScope.launch {
            delay(750)
            listOfTodo = repository.getTodos().toMutableList()
            _onDatabaseDataEvent.postValue(listOfTodo)
        }
    }

    fun updateTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }
}
