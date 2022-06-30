package com.example.seigenjikan

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.seigenjikan.databinding.FragmentBattleBinding


class BattleFragment : Fragment() {
    private var _binding: FragmentBattleBinding? = null
    private val binding get() = _binding!!
    private lateinit var Command3: Command3Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBattleBinding.inflate(inflater, container, false)
        var position:String? = ""
        // Bundleを取得する
        val bundle = arguments
        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            position = bundle.getString("KEY_POSITION")
        }
        //packageName取得
        val packageName = BuildConfig.APPLICATION_ID

        val imageId = resources.getIdentifier(position, "drawable", packageName) //リソースIDのを取得
        binding.enemyimage.setImageResource(imageId)

        val inflateX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.9f, 0.87f)
        val inflateY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.9f, 0.87f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.enemyimage, inflateX, inflateY).apply {
            duration = 1500
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }
        animator.start()
        //binding.com31.visibility = View.VISIBLE
        //binding.com31.visibility = View.INVISIBLE
        return binding.root
    }

}