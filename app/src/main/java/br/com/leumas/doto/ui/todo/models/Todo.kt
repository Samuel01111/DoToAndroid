package br.com.leumas.doto.ui.todo.models

data class Todo(
    val title: String,
    val description: String,
    val isFavorite: Boolean,
    val isCompleted: Boolean,
    val createDate: String
)
