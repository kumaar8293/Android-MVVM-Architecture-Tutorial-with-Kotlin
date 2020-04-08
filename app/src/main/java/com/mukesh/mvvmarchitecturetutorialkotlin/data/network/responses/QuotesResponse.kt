package com.mukesh.mvvmarchitecturetutorialkotlin.data.network.responses

import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.Quote

data class QuotesResponse(
    val isSuccessful: Boolean,
    val quote: List<Quote>
)
