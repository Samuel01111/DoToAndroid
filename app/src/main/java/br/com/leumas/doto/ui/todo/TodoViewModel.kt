package br.com.leumas.doto.ui.todo

import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    var listOfTodo = listOf<Todo>()

    init {
        getListOfTodo()
    }

    private fun getListOfTodo() {
        listOfTodo = listOf(
            Todo(
                title = "VAMO TREINA",
                description = "não esquece do pré treino em mano tem uma par de coisa pra nós pega lá po",
                isFavorite = true,
                isCompleted = false
            ),
            Todo(
                title = "VAMO TREINA",
                description = "não esquece do pré treino em mano",
                isFavorite = true,
                isCompleted = false
            ),
            Todo(
                title = "VAMO TREINA",
                description = "não esquece do pré treino em mano, inclusive a agua tbm",
                isFavorite = true,
                isCompleted = false
            ),
            Todo(
                title = "VAMO TREINA",
                description = "não esquece do pré ",
                isFavorite = true,
                isCompleted = false
            ),
            Todo(
                title = "VAMO TREINA",
                description = "não esquece do pré treino em mano",
                isFavorite = true,
                isCompleted = false
            )
        )
    }
}