package br.com.leumas.doto.ui.models

data class Todo(
    val id: Long,
    val title: String,
    val description: String,
    val isFavorite: Boolean,
    val isCompleted: Boolean,
    val createDate: String
)
