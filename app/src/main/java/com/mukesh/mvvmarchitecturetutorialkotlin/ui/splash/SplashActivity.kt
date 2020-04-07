package com.mukesh.mvvmarchitecturetutorialkotlin.ui.splash

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mukesh.mvvmarchitecturetutorialkotlin.R
import com.mukesh.mvvmarchitecturetutorialkotlin.ui.auth.SignInActivity


/**
 * Simple Splash activity with some Transition
 * You can validate your Login details and send to
 * activity accordingly
 */
class SplashActivity : AppCompatActivity() {
    //Variables
    private lateinit var topAnim: Animation
    private lateinit var bottomAnim: Animation
    private lateinit var rightToCenter: Animation
    private val handler: Handler? = Handler()
    private val myRunnable =
        MyRunnable()
    private lateinit var appLogo: ImageView
    private lateinit var appTitle: TextView
    private lateinit var tagLine: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //To hide Status bar or we can do with Theme also
//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash)
        initAnimation()
        iniView()
        sendToNextActivity()
    }

    //Initialisation of all the views
    private fun iniView() {
        appLogo = findViewById(R.id.image_logo)
        appTitle = findViewById(R.id.title)
        tagLine = findViewById(R.id.tag_line)
        //Setting the Animation
        appLogo.animation = rightToCenter
        appTitle.animation = topAnim
        tagLine.animation = bottomAnim
    }

    private fun initAnimation() { //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top)
        rightToCenter =
            AnimationUtils.loadAnimation(this, R.anim.right_to_center)
    }

    private fun sendToNextActivity() {
        handler!!.postDelayed(myRunnable, 2000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        handler?.removeCallbacks(myRunnable)
        finish()
    }

    private inner class MyRunnable : Runnable {
        override fun run() {
            //Attach all the elements those you want to animate in design
            // Pair Class uses same key value type pair
            // Key is the View to be animated while transition
            // transitionName matches with SignInActivity XML elements

            val pairs: Array<Pair<View, String>?> = arrayOfNulls(3)
            pairs[0] = Pair(appLogo, "app_icon")
            pairs[1] = Pair(
                appTitle, getString(R.string.hello_there_welcome_back)
            )
            pairs[2] = Pair(tagLine, getString(R.string.sign_in_to_continue))
            val intent = Intent(this@SplashActivity, SignInActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this@SplashActivity, *pairs
            )
            startActivity(intent, options.toBundle())
            onBackPressed()
        }
    }

}