package com.example.seigenjikan

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.seigenjikan.databinding.FragmentBattle3Binding
import java.util.ArrayList


class Battle3Fragment : Fragment() {
    private var _binding: FragmentBattle3Binding? = null
    private val binding get() = _binding!!
    private lateinit var command5: Command5Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getItem(com: ArrayList<Int>): Fragment? {
        // Bundle（オブジェクトの入れ物）のインスタンスを作成する
        val bundle = Bundle()
        // Key/Pairの形で値をセットする
        bundle.putIntegerArrayList("KEY_POSITION2", com)
        // Fragmentに値をセットする
        command5.arguments = bundle
        return command5
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

        /*command5= Command5Fragment()
        getItem(arrayListOf(1,2,3,3,3))
        val transaction: FragmentTransaction = childFragmentManager
                .beginTransaction()
        transaction.add(R.id.comFrame, command5, "child_1")
        transaction.commit()*/

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