package br.com.leumas.doto.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import br.com.leumas.doto.ui.extentions.isNull
import br.com.leumas.doto.ui.extentions.navigateWithAnimations
import br.com.leumas.doto.ui.models.Todo
import com.facebook.shimmer.ShimmerFrameLayout
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

    private lateinit var shimmer: ShimmerFrameLayout

    private lateinit var emptyTextView: TextView

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
        val layoutManager = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )

        recyclerView = recycler_view_todo_list
        shimmer = placeholder_group
        emptyTextView = text_view_empty_list

        emptyTextView.visibility = View.GONE
        recyclerView.visibility = View.GONE
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()

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
            if (!it.isNull()) {
                setupRecyclerView(recyclerView, layoutManager, it ?: emptyList())

                recyclerView.visibility = View.VISIBLE
                shimmer.visibility = View.GONE
            }

            if (it?.isEmpty() == true) {
                emptyTextView.visibility = View.VISIBLE
                emptyTextView.text = getString(R.string.todo_empty_list)
                emptyTextView.gravity = Gravity.CENTER
                recyclerView.visibility = View.GONE
                shimmer.visibility = View.GONE
            }
        }
    }

    private fun openAddTodoScreen() {
        clearList()
        navigationController.navigateWithAnimations(R.id.action_todoFragment_to_addTodoFragment)
    }

    fun clearList() {
        viewModel.listOfTodo.clear()
    }

    override fun onPause() {
        super.onPause()
        shimmer.stopShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        shimmer.stopShimmer()
    }

    fun updateTodoIntoDataBase(todo: TodoEntity) {
        Log.d("todooo", todo.isCompleted.toString() + todo.isFavorite.toString())
        viewModel.updateTodo(todo)
    }
}
