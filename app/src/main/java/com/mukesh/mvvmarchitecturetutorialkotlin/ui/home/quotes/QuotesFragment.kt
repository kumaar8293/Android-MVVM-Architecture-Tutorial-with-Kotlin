package com.mukesh.mvvmarchitecturetutorialkotlin.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mukesh.mvvmarchitecturetutorialkotlin.R
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.Coroutines
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.myToast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by kodein()
    val factory: QuotesViewModelFactory by instance()
    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(QuotesViewModel::class.java)

        //quotes is type is Deferred<LiveData<List<Quote>>> and we can't call the suspend fun directly
        //We will use await function to get LiveData updated

        Coroutines.main {
            val quotes = viewModel.quotes.await()

            quotes.observe(viewLifecycleOwner, Observer {

                context?.myToast("List of Quotes ${quotes.value?.size}")
            })
        }

    }
}
