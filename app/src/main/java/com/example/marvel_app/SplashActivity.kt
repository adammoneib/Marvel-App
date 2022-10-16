package com.example.marvel_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.marvel_app.databinding.ActivitySplashBinding
import jp.wasabeef.glide.transformations.BlurTransformation
import java.util.*
import kotlin.concurrent.schedule


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(R.drawable.mcu_background)
            .apply(bitmapTransform(BlurTransformation(22)))
            .into(binding.bgIv)
    }

    override fun onResume() {
        super.onResume()
        Timer("", false).schedule(2500) {
            startActivity(MainActivity.getIntent(this@SplashActivity))
            finish()
        }
    }
}