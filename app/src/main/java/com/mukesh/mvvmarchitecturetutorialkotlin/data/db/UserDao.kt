package com.mukesh.mvvmarchitecturetutorialkotlin.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.CURRENT_USER_ID
import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * from user_table where uid=$CURRENT_USER_ID")
    fun getUserData(): LiveData<User>
}