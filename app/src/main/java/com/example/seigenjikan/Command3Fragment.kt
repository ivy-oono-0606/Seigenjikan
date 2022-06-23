package com.example.seigenjikan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seigenjikan.databinding.FragmentCommand3Binding

class Command3Fragment : Fragment() {
    private var _binding: FragmentCommand3Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentCommand3Binding.inflate(inflater, container, false)
        var com :Int = 0
        // Bundleを取得する
        val bundle = arguments
        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            com = bundle.getInt("KEY_POSITION2")
        }

        val packageName = BuildConfig.APPLICATION_ID
        fun comin(com:Int):Int{
            var imageId:Int = 0
            if (com == 1){
                imageId = resources.getIdentifier("redbutton", "drawable", packageName) //リソースIDのを取得
            }else if (com == 2){
                imageId = resources.getIdentifier("bluebutton", "drawable", packageName) //リソースIDのを取得
            }else{
                imageId = resources.getIdentifier("greenbutton", "drawable", packageName) //リソースIDのを取得
            }
            return imageId
        }
        binding.com31.setImageResource(comin(com/100))
        binding.com32.setImageResource(comin(com%100/10))
        binding.com33.setImageResource(comin(com%100%10))
        return binding.root
    }

}