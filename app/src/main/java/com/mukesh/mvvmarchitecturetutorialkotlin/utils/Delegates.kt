package com.mukesh.mvvmarchitecturetutorialkotlin.utils

import kotlinx.coroutines.*

/**
 * Here we are making a generic function which takes suspend CoroutineScope.() functino and return type T
 * and lazyDeferred will return Deferred<T>
 * Deferred is basically a Job with Result
 */

fun<T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {

    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}