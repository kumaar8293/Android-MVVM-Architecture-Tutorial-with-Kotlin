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
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.mukesh.mvvmarchitecturetutorialkotlin.ui.home.HomeActivity
import com.mukesh.mvvmarchitecturetutorialkotlin.utils.*
import kotlinx.android.synthetic.main.activity_signin.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


const val TAG: String = "SignInActivity"

class SignInActivity : AppCompatActivity(), View.OnClickListener, AuthListener, KodeinAware {

    private lateinit var appLogo: ImageView
    private lateinit var tvWelcomeThere: TextView
    private lateinit var tvSignInToContinue: TextView
    private lateinit var username: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var goButton: Button
    private lateinit var signUpButton: Button
    private lateinit var editUserName: TextInputEditText
    private lateinit var editPassword: TextInputEditText

    //This is how we get instances fom Kodein
    // You will get an error with kodein() method then
    // You need to import import org.kodein.di.android.kodein manually
    override val kodein: Kodein by kodein() //Here we get kodein instance
    val factory: AuthViewModelFactory by instance()
    //This is how we get instance from Kodein, This will simply return AuthViewModelFactory instance


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_signin)
        /** In this activity we need AuthViewModelFactory to initialize ViewModel for LoginActivity
         * Which takes UserRepository as arguments
         * Following is the old techniques to Handle Constructor Dependency injection
         * Now we will use KODEIN
         * > Simply implement the KodeinAware interface
         * >Then get koding
         * >Then get AuthViewModelFactory instance from kodein as above ,, class level
         *
         */
        /*
         //I am doing Constructor dependencies injection
         val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
         val myApi = MyApi(networkConnectionInterceptor)
         val db = AppDatabase(this)
         val repository = UserRepository(db, myApi)*/

        //I am using ViewModelFactory pattern ,so that i can pass the repository to viewmodel

      //  val factory = AuthViewModelFactory(repository)

        val binding: ActivitySigninBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signin)

        // Use the 'by viewModels()' Kotlin property delegate
        // from the activity-ktx artifact
        // val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        val viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        // val viewModel: AuthViewModel by viewModels()

        binding.viewmodel = viewModel
        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer {
            if (it != null) {
                Intent(this, HomeActivity::class.java).also { intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }

            }
        })



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
       /* when (view?.id) {
            R.id.goButton -> loginValidation()
            R.id.signUpButton -> openSignUpPage()
            else -> {
            }
        }*/
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

        //  root_view.simpleSnackbar("Login success ${user.name}")
    }

    override fun onFailed(message: String) {
//        myToast("Login failed : $message")
        progress_bar.hideProgressBar()
        root_view.snackbarWithAction(message)
    }
}
