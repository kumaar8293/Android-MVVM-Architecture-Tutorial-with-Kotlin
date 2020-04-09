package com.mukesh.mvvmarchitecturetutorialkotlin

import android.app.Application
import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.AppDatabase
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.MyApi
import com.mukesh.mvvmarchitecturetutorialkotlin.data.network.NetworkConnectionInterceptor
import com.mukesh.mvvmarchitecturetutorialkotlin.data.preferences.PreferenceProvider
import com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories.QuotesRepository
import com.mukesh.mvvmarchitecturetutorialkotlin.data.repositories.UserRepository
import com.mukesh.mvvmarchitecturetutorialkotlin.ui.auth.AuthViewModelFactory
import com.mukesh.mvvmarchitecturetutorialkotlin.ui.home.profile.ProfileViewModelFactory
import com.mukesh.mvvmarchitecturetutorialkotlin.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplicationClass : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        /**
         * Steps to Use Koein
         * 1-> First implement the KodeinAware interface
         * 2-> Initialize Kodein with Lazy initialization
         * 3-> Initialize all the Classes using bind()
         */
        import(androidXModule(this@MVVMApplicationClass))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        // MyApi() takes NetworkConnectionInterceptor as a parameter
        //We can pass instance() method and Kodein will take care of it
        //Only rule is NetworkConnectionInterceptor should be initialize before passing to MyApi()
        bind() from singleton { MyApi(instance()) }
        //AppDatabase needs only Context
        bind() from singleton { AppDatabase(instance()) }
        //UserRepository needs AppDatabase and MyApi instances
        bind() from singleton { UserRepository(instance(), instance()) }

        //AuthViewModelFactory doesn't should be singleton so we will user provider
        //AuthViewModelFactory needs UserRepository
        bind() from provider { AuthViewModelFactory(instance()) }

        bind() from provider { ProfileViewModelFactory(instance()) }

        bind() from provider { PreferenceProvider(instance()) }
        bind() from provider { QuotesRepository(instance(),instance(),instance())}

        bind() from provider { QuotesViewModelFactory(instance()) }

    }
}