package br.com.leumas.doto.ui.di

import androidx.lifecycle.ViewModel
import br.com.leumas.doto.di.ViewModelKey
import br.com.leumas.doto.ui.TodoViewModel
import br.com.leumas.doto.ui.add.AddTodoViewModel
import br.com.leumas.doto.ui.edit.EditTodoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel::class)
    fun bindTodoViewModel(viewModel: TodoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditTodoViewModel::class)
    fun bindEditTodoViewModel(viewModel: EditTodoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTodoViewModel::class)
    fun bindAddTodoViewModel(viewModel: AddTodoViewModel): ViewModel
}
