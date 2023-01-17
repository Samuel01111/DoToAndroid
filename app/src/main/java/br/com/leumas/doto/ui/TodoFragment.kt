package br.com.leumas.doto.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.leumas.doto.MainActivity
import br.com.leumas.doto.R
import br.com.leumas.doto.data.db.TodoEntity
import br.com.leumas.doto.ui.adapter.TodoListAdapter
import br.com.leumas.doto.ui.extentions.navigateWithAnimations
import br.com.leumas.doto.ui.models.Todo
import kotlinx.android.synthetic.main.todo_fragment.*
import javax.inject.Inject

class TodoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<TodoViewModel> { viewModelFactory }

    private val navigationController: NavController by lazy {
        findNavController()
    }

    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity)
            .mainComponent
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.todo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = recycler_view_todo_list
        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )

        viewModel.getAllTodo()
        setupRecyclerView(recyclerView, layoutManager, viewModel.listOfTodo)
        listenToOnAddTodoButtonClicked()
        listenToOnDatabaseDataEvent(recyclerView, layoutManager)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: LayoutManager,
        list: List<Todo>
    ) {
        recyclerView.adapter = TodoListAdapter(this, list)
        recyclerView.layoutManager = layoutManager
    }

    private fun listenToOnAddTodoButtonClicked() {
        floatingButtonAddTodo.setOnClickListener {
            openAddTodoScreen()
        }
    }

    private fun listenToOnDatabaseDataEvent(
        recyclerView: RecyclerView,
        layoutManager: LayoutManager,
    ) {
        viewModel.onDatabaseDataEvent.observe(viewLifecycleOwner) {
            setupRecyclerView(recyclerView, layoutManager, it)
        }
    }

    private fun openAddTodoScreen() {
        viewModel.listOfTodo.clear()
        navigationController.navigateWithAnimations(R.id.action_todoFragment_to_addTodoFragment)
    }

    fun updateTodoIntoDataBase(todo: TodoEntity) {
        Log.d("todooo", todo.isCompleted.toString() + todo.isFavorite.toString())
        viewModel.updateTodo(todo)
    }
}
