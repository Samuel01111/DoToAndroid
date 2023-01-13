package br.com.leumas.doto.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.leumas.doto.data.db.dao.TodoDao

@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    //really good way to only instantiate the db only once.
    companion object {
        //Singleton
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
