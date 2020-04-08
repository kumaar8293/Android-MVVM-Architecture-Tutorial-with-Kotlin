package com.mukesh.mvvmarchitecturetutorialkotlin.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mukesh.mvvmarchitecturetutorialkotlin.R
import com.mukesh.mvvmarchitecturetutorialkotlin.databinding.ProfileFragmentBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class ProfileFragment : Fragment(), KodeinAware {

    //For fragment we need to import org.kodein.di.android.x.kodein
    override val kodein by kodein()

    private val factory: ProfileViewModelFactory by instance()

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ProfileFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)

        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        binding.viewmodel = viewModel
        /**
         * NOTE : As we are using LiveData, We need LifeCycleOwner
         * binding.lifecycleOwner=this
         */
        binding.lifecycleOwner = this
        return binding.root

    }

}
