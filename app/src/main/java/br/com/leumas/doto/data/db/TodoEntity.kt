package br.com.leumas.doto.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.leumas.doto.ui.models.Todo

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val isFavorite: Boolean,
    val isCompleted: Boolean,
    val createDate: String
)

fun Todo.toTodoEntity(id: Int = 0): TodoEntity {
    return with(this) {
        TodoEntity(
            id = id.toLong(),
            title = this.title,
            description = this.description,
            isFavorite = this.isFavorite,
            isCompleted = this.isCompleted,
            createDate = this.createDate
        )
    }
}

fun TodoEntity.toTodo(): Todo {
    return with(this) {
        Todo(
            id = this.id,
            title = this.title,
            description = this.description,
            isFavorite = this.isFavorite,
            isCompleted = this.isCompleted,
            createDate = this.createDate
        )
    }
}

fun Array<TodoEntity>.toTodoList(): List<Todo> {
    return this.map {
        Todo(
            id = it.id,
            title = it.title,
            description = it.description,
            isFavorite = it.isFavorite,
            isCompleted = it.isCompleted,
            createDate = it.createDate
        )
    }
}
