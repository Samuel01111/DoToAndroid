package br.com.leumas.doto.di

import android.content.Context
import br.com.leumas.doto.data.db.AppDataBaseModule
import br.com.leumas.doto.ui.di.MainComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataModule::class,
    AppDataBaseModule::class,
    ViewModelBuilderModule::class,
    SubcomponentsModule::class,
    StringModule::class
])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun mainComponent(): MainComponent.Factory
}

@Module(subcomponents = [MainComponent::class])
object SubcomponentsModule
