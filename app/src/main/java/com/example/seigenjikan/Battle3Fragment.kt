package com.example.seigenjikan

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seigenjikan.databinding.FragmentBattle3Binding
import com.example.seigenjikan.databinding.FragmentBattleBinding


class Battle3Fragment : Fragment() {
    private var _binding: FragmentBattle3Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBattle3Binding.inflate(inflater, container, false)
        var position:String? = ""
        var text:String? = ""
        // Bundleを取得する
        val bundle = arguments
        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            position = bundle.getString("KEY_POSITION")
            text = bundle.getString("KEY_POSITION2")
        }
        //packageName取得
        val packageName = BuildConfig.APPLICATION_ID
        val imageId = resources.getIdentifier(position, "drawable", packageName) //リソースIDのを取得

        binding.Enemy3ImageView.setImageResource(imageId)
        binding.QuestionTextView.text = text
        val inflateX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.9f, 0.87f)
        val inflateY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.9f, 0.87f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.Enemy3ImageView, inflateX, inflateY).apply {
            duration = 1500
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }
        animator.start()

        return binding.root
    }
}