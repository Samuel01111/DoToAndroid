package br.com.leumas.doto.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.leumas.doto.R
import br.com.leumas.doto.ui.todo.adapter.TodoListAdapter
import kotlinx.android.synthetic.main.row_todo_layout.*
import kotlinx.android.synthetic.main.todo_fragment.*

class TodoFragment : Fragment() {

   private val viewModel: TodoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.todo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = recycler_view_todo_list
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        recyclerView.adapter = TodoListAdapter(this, viewModel.listOfTodo)
        recyclerView.layoutManager = layoutManager
    }
}
