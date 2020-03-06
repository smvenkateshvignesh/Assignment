package com.ci.assignment.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.ci.assignment.R
import com.ci.assignment.ui.category.CategoryActivity

class SplashScreenActivity : AppCompatActivity() {
private val splashScreenDuration = 2300L

    override fun onCreate(savedInstanceState: Bundle?) {
        showOrHideStatusBar(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        navigateToCategoryScreen()
    }

    private fun navigateToCategoryScreen() {
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, CategoryActivity::class.java)
            startActivity(intent)
            finish()
        }, splashScreenDuration)
    }

    fun showOrHideStatusBar(isStatusBarVisible: Boolean) {
        window.apply {
            if (isStatusBarVisible) {
                addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }
    }
}
