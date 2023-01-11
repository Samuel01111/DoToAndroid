package br.com.leumas.doto.di

import br.com.leumas.doto.data.TodoRepository
import br.com.leumas.doto.data.TodoRepositoryImp
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {

    //who needs a (TodoRepository)
    @Singleton
    @Binds
    abstract fun provideLocalDataSource(repositoryImp: TodoRepositoryImp): TodoRepository
}