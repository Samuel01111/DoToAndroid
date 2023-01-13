package br.com.leumas.doto.di

import br.com.leumas.doto.data.repository.TodoDbDataSource
import br.com.leumas.doto.data.repository.TodoRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {

    //who needs a (TodoRepository)
    @Singleton
    @Binds
    abstract fun provideLocalDataSource(todoDbDataSource: TodoDbDataSource): TodoRepository
}