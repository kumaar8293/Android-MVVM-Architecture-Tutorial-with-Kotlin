package com.mukesh.mvvmarchitecturetutorialkotlin.ui.auth

import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.mukesh.mvvmarchitecturetutorialkotlin.R
import com.mukesh.mvvmarchitecturetutorialkotlin.data.db.entities.User
import com.mukesh.mvvmarchitecturetutorialkotlin.databinding.ActivitySigninBinding
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.*
import kotlinx.android.synthetic.main.activity_signin.*


class SignInActivity : AppCompatActivity(), View.OnClickListener, AuthListener {

    private lateinit var appLogo: ImageView
    private lateinit var tvWelcomeThere: TextView
    private lateinit var tvSignInToContinue: TextView
    private lateinit var username: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var goButton: Button
    private lateinit var signUpButton: Button
    private lateinit var editUserName: TextInputEditText
    private lateinit var editPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_signin)

        val binding: ActivitySigninBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signin)

        // Use the 'by viewModels()' Kotlin property delegate
        // from the activity-ktx artifact
        // val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        val viewModel: AuthViewModel by viewModels()

        binding.viewmodel = viewModel
        viewModel.authListener = this
        checkPermission()

        initView()
    }


    private fun initView() {
        appLogo = findViewById(R.id.logo_image)
        tvWelcomeThere = findViewById(R.id.welcome_message)
        tvSignInToContinue = findViewById(R.id.sign_in_message)
        username = findViewById(R.id.userEmail)
        password = findViewById(R.id.password)
        goButton = findViewById(R.id.goButton)
        signUpButton = findViewById(R.id.signUpButton)
        editPassword = findViewById(R.id.editPassword)
        editUserName = findViewById(R.id.editUserEmail)
        goButton.setOnClickListener(this)
        signUpButton.setOnClickListener(this)
    }


    private fun checkPermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_PHONE_STATE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */
                }
            }).check()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.goButton -> loginValidation()
            R.id.signUpButton -> openSignUpPage()
            else -> {
            }
        }
    }

    private fun loginValidation() {
        var boolean = editUserName.isEmpty()
    }

    private fun openSignUpPage() {
        val pairs: Array<Pair<View, String>?> = arrayOfNulls(7)
        pairs[0] = Pair(appLogo, "app_icon")
        pairs[1] = Pair(tvWelcomeThere, getString(R.string.welcome))
        pairs[2] = Pair(tvSignInToContinue, getString(R.string.signup_to_start_your_new_journey))
        pairs[3] = Pair(username, getString(R.string.username))

        pairs[4] = Pair(password, getString(R.string.password))
        pairs[5] = Pair(goButton, getString(R.string.sign_up))
        pairs[6] = Pair(signUpButton, getString(R.string.already_have_an_account_login))
        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)

        val options = ActivityOptions.makeSceneTransitionAnimation(
            this@SignInActivity, *pairs
        )
        startActivity(intent, options.toBundle())
    }

    override fun onStarted() {
        hideKeyBoard()
        progress_bar.showProgressBar()

        myToast("Login Started")
    }

    override fun onSuccess(user: User) {
        progress_bar.hideProgressBar()
//        myToast("Login success ${user.name}")

        root_view.simpleSnackbar("Login success ${user.name}")
    }

    override fun onFailed(message: String) {
//        myToast("Login failed : $message")
        progress_bar.hideProgressBar()
        root_view.snackbarWithAction(message)
    }
}
