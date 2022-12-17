package br.com.leumas.doto.ui.todo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.com.leumas.doto.R
import br.com.leumas.doto.ui.todo.Todo
import kotlinx.android.synthetic.main.row_todo_layout.view.*

class TodoListAdapter(
    private val fragment: Fragment,
    private val todos: List<Todo>
    ): RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(fragment.context).inflate(R.layout.row_todo_layout, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val note = todos[position]

        holder.bindView(note)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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

