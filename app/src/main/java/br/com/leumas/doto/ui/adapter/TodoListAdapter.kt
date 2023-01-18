package br.com.leumas.doto.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.leumas.doto.R
import br.com.leumas.doto.data.db.toTodoEntity
import br.com.leumas.doto.ui.TodoFragment
import br.com.leumas.doto.ui.TodoFragmentDirections
import br.com.leumas.doto.ui.models.Todo
import kotlinx.android.synthetic.main.row_todo_layout.view.*

class TodoListAdapter(
    private val fragment: TodoFragment,
    private val todos: List<Todo>
) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =
            LayoutInflater.from(fragment.context).inflate(R.layout.row_todo_layout, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]

        holder.itemView.row_todo_layout_container.setOnClickListener {
            fragment.clearList()
            val directions = TodoFragmentDirections
                .actionTodoFragmentToEditTodoFragment(position + 1)
            fragment.findNavController().navigate(directions)
        }

        holder.itemView.checkbox_todo_complete.setOnClickListener {
            it.checkbox_todo_complete.isChecked
            val newTodo = todo.copy(
                isCompleted = it.checkbox_todo_complete.isChecked
            )
            fragment.updateTodoIntoDataBase(
                newTodo.toTodoEntity(
                    position + 1
                )
            )
        }

        holder.itemView.checkbox_todo_favorite.setOnClickListener {
            it.checkbox_todo_favorite.isChecked
            val newTodo = todo.copy(
                isFavorite = it.checkbox_todo_favorite.isChecked
            )
            fragment.updateTodoIntoDataBase(
                newTodo.toTodoEntity(
                    position + 1
                )
            )
        }

        holder.bindView(todo)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(todo: Todo) {
            val title = itemView.textview_title
            val description = itemView.textview_description
            val isFavorite = itemView.checkbox_todo_favorite
            val isCompleted = itemView.checkbox_todo_complete

            title.text = todo.title
            description.text = todo.description
            isCompleted.isChecked = todo.isCompleted
            isFavorite.isChecked = todo.isFavorite
        }
    }
}
