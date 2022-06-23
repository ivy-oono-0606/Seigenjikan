package com.example.seigenjikan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seigenjikan.databinding.FragmentBattle2Binding
import com.example.seigenjikan.databinding.FragmentBattleBinding


class Battle2Fragment : Fragment() {
    private var _binding: FragmentBattle2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBattle2Binding.inflate(inflater, container, false)

        var position:String? = ""
        // Bundleを取得する
        val bundle = arguments
        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            position = bundle.getString("KEY_POSITION")
        }
        //packageName取得
        val packageName = BuildConfig.APPLICATION_ID
        val imageId = resources.getIdentifier("test", "drawable", packageName) //リソースIDのを取得

        binding.Enemy2ImageView.setImageResource(imageId)

        return binding.root
    }
}