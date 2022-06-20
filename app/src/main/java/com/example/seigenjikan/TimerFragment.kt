package com.example.seigenjikan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.seigenjikan.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    private lateinit var batle: BattleFragment

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        var position = 0
        // Bundleを取得する
        val bundle = arguments
        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            position = bundle.getInt("KEY_POSITION")
        }
        binding.timerText2.text = position.toString()

        binding.titleButton3.setOnClickListener {
            val fragmentManager = fragmentManager
            batle = BattleFragment()
            if(fragmentManager != null) {
                fragmentManager.beginTransaction().remove(this).commit()
                fragmentManager.popBackStack()
                //fragmentManager.popBackStack("batle", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
        return binding.root
    }
}