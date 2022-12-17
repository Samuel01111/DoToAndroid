package br.com.leumas.doto.ui.todo

data class Todo(
    val title: String,
    val description: String,
    val isFavorite: Boolean,
    val isCompleted: Boolean
)