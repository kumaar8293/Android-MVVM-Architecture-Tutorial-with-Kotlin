package com.mukesh.mvvmarchitecturetutorialkotlin.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//It is similar to static in java
object Coroutines {

    /**
     * Here main() function will take another function as parameters (work)
     * work() function is a suspended (Coroutines function)
     * work() function won't take any parameters and it will return Unit , similar to Void
     * Dispatchers will decide on which thread we will call this function
     *Dispatchers.Main it will use the main thread
     *
     * Here we are simply taking a function work() as a parameter and
     * executing that function inside CoroutineScope()
     *
     */
    fun main(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }
}