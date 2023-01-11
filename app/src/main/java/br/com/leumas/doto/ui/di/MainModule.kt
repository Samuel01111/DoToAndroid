package br.com.leumas.doto.ui.di

import androidx.lifecycle.ViewModel
import br.com.leumas.daggerApp.di.ViewModelKey
import br.com.leumas.doto.ui.TodoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel::class)
    fun bindTodoViewModel(viewModel: TodoViewModel): ViewModel
}