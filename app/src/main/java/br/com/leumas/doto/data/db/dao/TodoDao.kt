package br.com.leumas.doto.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.leumas.doto.data.db.TodoEntity

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTodo(user: TodoEntity)

    @Query("select * from todo_table")
    suspend fun getTodos(): Array<TodoEntity>

    @Query("select * from todo_table where id == :id")
    suspend fun getTodoById(id: Int): TodoEntity

    @Query("delete from todo_table where id == :id")
    suspend fun deleteTodoById(id: Int)
}
