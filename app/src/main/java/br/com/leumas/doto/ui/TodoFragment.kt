package br.com.leumas.doto.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.leumas.doto.MainActivity
import br.com.leumas.doto.R
import br.com.leumas.doto.ui.adapter.TodoListAdapter
import kotlinx.android.synthetic.main.todo_fragment.*
import javax.inject.Inject

class TodoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<TodoViewModel> { viewModelFactory }

    private val navigationController: NavController by lazy {
        findNavController()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity)
            .mainComponent
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.todo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = recycler_view_todo_list
        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )

        recyclerView.adapter = TodoListAdapter(this, viewModel.listOfTodo)
        recyclerView.layoutManager = layoutManager


        listenToOnAddTodoButtonClicked()
    }

    private fun listenToOnAddTodoButtonClicked() {
        floatingButtonAddTodo.setOnClickListener {
            openAddTodoScreen()
        }
    }

    private fun openAddTodoScreen() {
        navigationController.navigate(R.id.action_todoFragment_to_addTodoFragment)
    }
}
