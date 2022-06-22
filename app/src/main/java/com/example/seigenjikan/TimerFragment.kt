package com.example.seigenjikan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        var position:String? = ""
        // Bundleを取得する
        val bundle = arguments
        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            position = bundle.getString("KEY_POSITION")
        }
        binding.timerText2.text = position

        binding.titleButton3.setOnClickListener {
            val fragmentManager = fragmentManager
            batle = BattleFragment()
            if(fragmentManager != null) {
                fragmentManager.beginTransaction().remove(this).commit()
                fragmentManager.popBackStack()
            }
        }
        return binding.root
    }
}