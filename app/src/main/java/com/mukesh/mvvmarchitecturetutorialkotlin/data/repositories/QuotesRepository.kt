package com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.AppDatabase
import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.Quote
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.MyApi
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.SafeApiRequest
import com.mukesh.mvvmarchitecturetutorialkotlin.data.preferences.PreferenceProvider
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuotesRepository(
    val myApi: MyApi, val db: AppDatabase, val preferenceProvider: PreferenceProvider
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        //Hence we are not inside Activity or Fragment,
        // we don't need to worry about LifeCycle
        quotes.observeForever {

            it?.let {
                saveQuote(it)
            }
        }
    }

    private fun saveQuote(quotes: List<Quote>) {
        Coroutines.ioThread {
            db.getQuoteDao().saveAllQuotes(quotes)
        }

    }

    private suspend fun fetchQuotes() {

        if (isFetchNeeded()) {
            val response = apiRequest {
                myApi.getQuotes()
            }
            quotes.postValue(response.quote)
        }
    }

    private fun isFetchNeeded(): Boolean {

        return true
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        //If we don't want to use suspend function to the DAO then we can use withContext()
        //To Define the CoroutineScope
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getAllTheQuotes()
        }
    }
}