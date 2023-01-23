package br.com.leumas.doto.ui.edit

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import br.com.leumas.doto.MainActivity
import br.com.leumas.doto.R
import br.com.leumas.doto.data.db.TodoEntity
import br.com.leumas.doto.databinding.FragmentEditTodoBinding
import br.com.leumas.doto.ui.add.AddTodoFragment.Companion.SIMPLE_DATE_FORMAT
import br.com.leumas.doto.ui.add.AddTodoViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_edit_todo.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

class EditTodoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<EditTodoViewModel> { viewModelFactory }

    private val args: EditTodoFragmentArgs by navArgs()

    private val navigationController: NavController by lazy {
        findNavController()
    }

    private var _binding: FragmentEditTodoBinding? = null

    private val binding get() = _binding!!

    private var todoId by Delegates.notNull<Int>()

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
        _binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        _binding?.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fieldsStateEvent.observe(viewLifecycleOwner) { state ->
            when(state) {
                is EditTodoViewModel.FieldState.InvalidFields -> {
                    val validationFields: Map<String, TextInputLayout> = initValidationFields()
                    state.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }

                is EditTodoViewModel.FieldState.ValidFields -> {
                    navigationController.popBackStack()
                }
            }
        }

        listenToOnSaveButtonClicked()
        listenToOnRemoveButtonClicked()
        setupEditScreenFields()
    }

    private fun listenToOnSaveButtonClicked() {
        buttonTodoSaveEdit.setOnClickListener {
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat(SIMPLE_DATE_FORMAT)

            val title = inputTodoTitleEdit.text.toString()
            val description = inputTodoDescriptionEdit.text.toString()
            val isFavorite = checkboxTodoFavoriteEditScreen.isChecked
            val isComplete = checkboxTodoCompleteEditScreen.isChecked
            val currentDate = formatter.format(time)

            if (viewModel.isValidForm(title, description)) {
                viewModel.updateTodo(
                    TodoEntity(
                        todoId.toLong(),
                        title,
                        description,
                        isFavorite,
                        isComplete,
                        currentDate
                    )
                )
            }
        }
    }

    private fun listenToOnRemoveButtonClicked() {
        buttonTodoRemoveEdit.setOnClickListener {

            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setMessage(R.string.dialog_todo_question)
                    setPositiveButton(R.string.dialog_todo_delete
                    ) { _, _ ->
                        viewModel.removeTodoById(todoId)
                        navigationController.popBackStack()
                    }
                    setNegativeButton(R.string.dialog_todo_cancel
                    ) { dialog, _ ->
                        dialog.cancel()
                    }
                }
                builder.create()
            }
            alertDialog?.show()
        }
    }

    private fun setupEditScreenFields() {
        todoId = args.todoId
        viewModel.fetchTodoById(todoId)
    }

    private fun initValidationFields() = mapOf(
        AddTodoViewModel.INPUT_TITLE.first to inputLayoutTodoTitleEdit,
        AddTodoViewModel.INPUT_DESCRIPTION.first to inputLayoutTodoDescriptionEdit
    )

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        viewModel.currentTodo.set(null)
    }
}
