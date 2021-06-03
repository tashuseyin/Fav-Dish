package com.example.favdish.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.favdish.MainActivity
import com.example.favdish.R
import com.example.favdish.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Animation
        val splashAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_splash)
        binding.tvAppName.animation = splashAnimation


        // Aşağıdaki gibi geri aramalar ile animasyon tamamlandıktan sonra herhangi bir işlem yapmak isteyip istemediğinizi göreceğiz.

        splashAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Animasyon başladığında yürütmek istediğiniz kodu ekleyin.
            }

            override fun onAnimationEnd(animation: Animation?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }, 1000)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Animasyon tekrar yürütüldüğünde yapmak istediğiniz kodu ekleyin.
            }
        })

    }
}