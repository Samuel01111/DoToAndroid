package br.com.leumas.doto.ui.adapter

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import br.com.leumas.doto.R
import br.com.leumas.doto.data.db.toTodoEntity
import br.com.leumas.doto.ui.TodoFragment
import br.com.leumas.doto.ui.TodoFragmentDirections
import br.com.leumas.doto.ui.extentions.navigateWithAnimations
import br.com.leumas.doto.ui.models.Todo
import kotlinx.android.synthetic.main.row_todo_layout.view.*


class TodoListAdapter(
    private val fragment: TodoFragment?,
    private val todos: List<Todo>
) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(fragment?.context).inflate(
            R.layout.row_todo_layout,
            parent,
            false
        )
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]

        holder.bindView(todo)

        val textTitle = holder.itemView.textview_title.text.toString()
        val textDescription = holder.itemView.textview_description.text.toString()

        listenToRowClicked(holder, todo)
        listenToOnCheckboxCompleteClicked(holder, todo, textTitle, textDescription)
        listenToOnCheckboxFavoriteClicked(holder, todo)
        setStringStrike(holder, textTitle, textDescription)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    private fun listenToOnCheckboxFavoriteClicked(
        holder: TodoViewHolder,
        todo: Todo
    ) {
        holder.itemView.checkbox_todo_favorite.setOnClickListener {
            it.checkbox_todo_favorite.isChecked
            val newTodo = todo.copy(
                isFavorite = it.checkbox_todo_favorite.isChecked
            )
            fragment?.updateTodoIntoDataBase(
                newTodo.toTodoEntity(
                    todo.id.toInt()
                )
            )
        }
    }

    private fun listenToOnCheckboxCompleteClicked(
        holder: TodoViewHolder,
        todo: Todo,
        textTitle: String,
        textDescription: String
    ) {
        holder.itemView.checkbox_todo_complete.setOnClickListener {
            val newTodo = todo.copy(
                isCompleted = it.checkbox_todo_complete.isChecked
            )

            fragment?.updateTodoIntoDataBase(
                newTodo.toTodoEntity(
                    todo.id.toInt()
                )
            )

            setStringStrike(holder, textTitle, textDescription)
        }
    }

    private fun listenToRowClicked(
        holder: TodoViewHolder,
        todo: Todo
    ) {
        holder.itemView.row_todo_layout_container.setOnClickListener {
            fragment?.clearList()
            val directions = TodoFragmentDirections
                .actionTodoFragmentToEditTodoFragment(todo.id.toInt())
            fragment?.findNavController()?.navigateWithAnimations(directions)
        }
    }

    private fun getStrikeThroughString(string: String): SpannableString {
        val stringSpannable = SpannableString(string)
        stringSpannable.setSpan(StrikethroughSpan(), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return stringSpannable
    }

    private fun setStringStrike(
        holder: TodoViewHolder,
        textTitle: String,
        textDescription: String
    ) {
        if (holder.itemView.checkbox_todo_complete.isChecked) {
            holder.itemView.textview_title.text = getStrikeThroughString(textTitle)
            holder.itemView.textview_description.text = getStrikeThroughString(textDescription)
        } else {
            holder.itemView.textview_title.text = textTitle
            holder.itemView.textview_description.text = textDescription
        }
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
