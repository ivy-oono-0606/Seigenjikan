package com.example.seigenjikan

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seigenjikan.databinding.ActivitySubBinding
import com.example.seigenjikan.databinding.FragmentTimerBinding



class TimerFragment : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*var timer = MyCountDownTimer(3 * 60 * 1000, 100)
        timer.start()
        timer.isRunning = true*/
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding =  FragmentTimerBinding.inflate(inflater, container, false)
        binding.timerText2.text = "3:00"
        return binding.root
    }
}