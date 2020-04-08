package com.mukesh.mvvmarchitecturetutorialkotlin.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.Quote

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllQuotes(quotes: List<Quote>)

    @Query("SELECT * FROM quotes_table")
    fun getAllTheQuotes(): LiveData<List<Quote>>

}