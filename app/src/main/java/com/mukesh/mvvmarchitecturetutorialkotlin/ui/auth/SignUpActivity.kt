package com.mukesh.mvvmarchitecturetutorialkotlin.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mukesh.mvvmarchitecturetutorialkotlin.R
import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.User
import com.mukesh.mvvmarchitecturetutorialkotlin.databinding.ActivitySignUpBinding
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignUpActivity : AppCompatActivity(), KodeinAware, AuthListener, View.OnClickListener {

    override val kodein: Kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignUpBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        val viewModel = ViewModelProvider(this, factory)
            .get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this


//        setContentView(R.layout.activity_sign_up)
        alreadyHaveAccount.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when (view?.id) {
            R.id.alreadyHaveAccount -> onBackPressed()

        }
    }

    override fun onStarted() {
        hideKeyBoard()
        progress_bar.showProgressBar()

       // myToast("Login Started")
    }

    override fun onSuccess(user: User) {
        progress_bar.hideProgressBar()
//        myToast("Login success ${user.name}")

        //  root_view.simpleSnackbar("Login success ${user.name}")
    }

    override fun onFailed(message: String) {
//        myToast("Login failed : $message")
        progress_bar.hideProgressBar()
        root_view.snackbarWithAction(message)
    }
}
