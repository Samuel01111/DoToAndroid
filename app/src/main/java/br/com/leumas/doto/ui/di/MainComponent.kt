package br.com.leumas.doto.ui.di

import br.com.leumas.doto.MainActivity
import br.com.leumas.doto.ui.TodoFragment
import br.com.leumas.doto.ui.add.AddTodoFragment
import br.com.leumas.doto.ui.edit.EditTodoFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: TodoFragment)
    fun inject(fragment: AddTodoFragment)
    fun inject(fragment: EditTodoFragment)
}
