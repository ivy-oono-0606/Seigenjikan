package com.example.seigenjikan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seigenjikan.databinding.FragmentCommand5Binding

class Command5Fragment : Fragment() {
    private var _binding: FragmentCommand5Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentCommand5Binding.inflate(inflater, container, false)
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
        binding.com51.setImageResource(comin(com/10000))
        binding.com52.setImageResource(comin(com%10000/1000))
        binding.com53.setImageResource(comin(com%10000%1000/100))
        binding.com54.setImageResource(comin(com%10000%1000%100/10))
        binding.com55.setImageResource(comin(com%10000%1000%100%10))
        return binding.root
    }

}