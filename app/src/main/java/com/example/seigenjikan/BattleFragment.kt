package com.example.seigenjikan

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.seigenjikan.databinding.FragmentBattleBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BattleFragment : Fragment() {
    private var _binding: FragmentBattleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentBattleBinding.inflate(inflater, container, false)
        /*val myImage: ImageView = findViewById(binding.enemyimage)
        myImage.setImageResource(R.drawable.sample)*/

        return binding.root
    }
}