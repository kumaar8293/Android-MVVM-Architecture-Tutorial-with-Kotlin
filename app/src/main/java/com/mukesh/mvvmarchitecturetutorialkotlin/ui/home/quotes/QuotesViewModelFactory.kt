package com.mukesh.mvvmarchitecturetutorialkotlin.ui.home.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories.QuotesRepository

@Suppress("UNCHECKED_CAST")
class QuotesViewModelFactory(
    private val quotesRepository: QuotesRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return QuotesViewModel(quotesRepository) as T
    }
}