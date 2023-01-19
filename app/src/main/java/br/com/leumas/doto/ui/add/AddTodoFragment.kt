package br.com.leumas.doto.ui.add

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
import br.com.leumas.doto.MainActivity
import br.com.leumas.doto.R
import br.com.leumas.doto.ui.extentions.navigateWithAnimations
import br.com.leumas.doto.ui.models.Todo
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_add_todo.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddTodoFragment : Fragment() {

    @Inject
    lateinit var viewModelProvider: ViewModelProvider.Factory

    private val viewModel by viewModels<AddTodoViewModel> { viewModelProvider }

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
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fieldsStateEvent.observe(viewLifecycleOwner) { state ->
            when(state) {
                is AddTodoViewModel.FieldState.InvalidFields -> {
                    val validationFields: Map<String, TextInputLayout> = initValidationFields()
                    state.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }

                is AddTodoViewModel.FieldState.ValidFields -> {
                    navigationController.navigateWithAnimations(R.id.action_addTodoFragment_to_todoFragment)
                }
            }
        }

        listenToButtonTodoSaveClicked()
    }

    private fun listenToButtonTodoSaveClicked() {
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
                viewModel.saveTodo(
                    Todo(
                        0,
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
        AddTodoViewModel.INPUT_TITLE.first to inputLayoutTodoTitle,
        AddTodoViewModel.INPUT_DESCRIPTION.first to inputLayoutTodoDescription
    )

    companion object {
        const val SIMPLE_DATE_FORMAT = "yyyy-MM-dd"
    }
}
