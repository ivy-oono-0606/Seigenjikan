package com.example.seigenjikan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.seigenjikan.databinding.FragmentBattleBinding

class BattleFragment : Fragment() {
    private var _binding: FragmentBattleBinding? = null
    private val binding get() = _binding!!

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
        var com :Int = 0
        // Bundleを取得する
        val bundle = arguments
        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            position = bundle.getString("KEY_POSITION")
            com = bundle.getInt("KEY_POSITION2")
        }
        //packageName取得
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
        val imageId = resources.getIdentifier(position, "drawable", packageName) //リソースIDのを取得
        binding.enemyimage.setImageResource(imageId)

        binding.com31.setImageResource(comin(com/100))
        binding.com32.setImageResource(comin(com%100/10))
        binding.com33.setImageResource(comin(com%100%10))
        //binding.com31.visibility = View.VISIBLE
        //binding.com31.visibility = View.INVISIBLE

        return binding.root
    }

}