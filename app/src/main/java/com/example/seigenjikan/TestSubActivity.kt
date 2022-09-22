package com.example.seigenjikan

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.seigenjikan.databinding.ActivityTestSubBinding

class TestSubActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestSubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestSubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inflateX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.05f, 0.9f)
        val inflateY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.05f, 0.9f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.TestImageView, inflateX, inflateY).apply {
            duration = 400
            repeatCount = ObjectAnimator.INFINITE
        }

        binding.TestButton.setOnClickListener{
            binding.TestImageView.visibility = View.VISIBLE;
            binding.TestImageView.setAlpha(100);
            animator.start()
            loadingDelay()
            //animator.pause()
        }
//        binding.Testbutton2.setOnClickListener{
//           binding.TestImageView.visibility = View.INVISIBLE;
//
//        }
    }
    //ここで一時的に処理を止めて0.5秒後に処理を再開する
    fun loadingDelay(){
        Handler().postDelayed({
            binding.TestImageView.visibility = View.INVISIBLE;
        }, 1000/2)
    }
}