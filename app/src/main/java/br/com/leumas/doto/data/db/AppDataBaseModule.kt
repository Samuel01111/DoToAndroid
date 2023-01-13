package br.com.leumas.doto.data.db

import android.content.Context
import br.com.leumas.doto.data.db.dao.TodoDao
import dagger.Module
import dagger.Provides

@Module
class AppDataBaseModule {

    @Provides
    fun providesTodoDao(
        context: Context
    ): TodoDao {
        val database = AppDatabase.getDatabase(context)
        return database.todoDao()
    }
}