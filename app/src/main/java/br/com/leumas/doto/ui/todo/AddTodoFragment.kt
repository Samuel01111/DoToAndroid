package br.com.leumas.doto.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import br.com.leumas.doto.R
import br.com.leumas.doto.ui.todo.extentions.navigateWithAnimations
import br.com.leumas.doto.ui.todo.models.Todo
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_add_todo.*
import java.text.SimpleDateFormat
import java.util.*

class AddTodoFragment : Fragment() {

    private val viewModel: TodoViewModel by activityViewModels()

    private val navigationController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fieldsStateEvent.observe(viewLifecycleOwner) { state ->
            when(state) {
                is TodoViewModel.FieldState.InvalidFields -> {
                    val validationFields: Map<String, TextInputLayout> = initValidationFields()
                    state.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }

                is TodoViewModel.FieldState.ValidFields -> {
                    navigationController.navigateWithAnimations(R.id.action_addTodoFragment_to_todoFragment)
                }
            }
        }

        listenToButtonTodoSaveClicked()
    }

    fun listenToButtonTodoSaveClicked() {
        buttonTodoSave.setOnClickListener {
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat(SIMPLE_DATE_FORMAT)

            val title = inputTodoTitle.text.toString()
            val description = inputTodoDescription.text.toString()
            val isFavorite = checkboxTodoFavoriteAddScreen.isChecked
            val isComplete = checkboxTodoCompleteAddScreen.isChecked
            val currentDate = formatter.format(time)

            if (viewModel.isValidForm(title, description)) {
                //persist it with Room
                viewModel.addTodoIntoList(
                    Todo(
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

    private fun initValidationFields() = mapOf(
        TodoViewModel.INPUT_TITLE.first to inputLayoutTodoTitle,
        TodoViewModel.INPUT_DESCRIPTION.first to inputLayoutTodoDescription
    )

    companion object {
        private const val SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
    }
}
