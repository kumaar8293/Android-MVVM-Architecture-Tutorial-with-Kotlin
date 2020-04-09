package com.mukesh.mvvmarchitecturetutorialkotlin.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories.QuotesRepository
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.lazyDeferred

class QuotesViewModel(private val quotesRepository: QuotesRepository) :
    ViewModel() {


    /**
     * I don't want to call getQuotes() always,whenever QuotesViewModel gets loaded
     * Hence I am going to use  lazy block
     * It will create only one instance
     * Hence getQuotes() is a suspend function, we can't call directly
     * > We will create our own Lazy block with CoroutineScope and it will allow
     * us to call the suspended function directly
     * Please check the Delegate.kt file
     */
    val quotes by lazyDeferred {
        quotesRepository.getQuotes()
    }


}
