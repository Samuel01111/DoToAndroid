package br.com.leumas.doto.ui.edit

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
import javax.inject.Inject

class EditTodoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<EditTodoViewModel> { viewModelFactory }

    private val args: EditTodoFragmentArgs by navArgs()

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
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEditScreenFields()
    }

    private fun setupEditScreenFields() {
        val id = args.todoId
        viewModel.fetchTodoById(id)

    }
}
