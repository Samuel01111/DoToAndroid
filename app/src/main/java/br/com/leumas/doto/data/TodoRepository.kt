package br.com.leumas.doto.data

import br.com.leumas.doto.ui.models.Todo

interface TodoRepository {

    fun getTodos()

    fun saveTodo(todo: Todo)
}
