package com.mukesh.mvvmarchitecturetutorialkotlin.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDoa(): UserDao

    companion object {

        @Volatile  //It means immediately visible to All the threads
        private var instance: AppDatabase? = null

        private val LOCK = Any() // We can also keep LOCK of Any type (AppDatabase.javaclass)

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

            instance ?: buildDatabase(context).also {
                instance=it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "User_database"
            ).build()
    }

}