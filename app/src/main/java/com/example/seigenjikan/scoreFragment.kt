package com.example.seigenjikan

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seigenjikan.databinding.FragmentScoreBinding

class scoreFragment : Fragment() {
    private var _binding:FragmentScoreBinding? = null
    private val binding get() = _binding!!
    private var listener:scoreListener?= null

    interface scoreListener {//上記と同名で定義
        fun backconfig()//ここでアクティビティのメソッドに渡します。
        fun textchange(int:Int)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        val bundle = arguments

        var str1:String? = "ストーリー"
        var str2:String? = ""
        var str3:String? = ""
        var str4:String? = ""
        var mode:Boolean = false

        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            str1 = bundle.getString("KEY_POSITION")
            str2 = bundle.getString("KEY_POSITION2")
            str3 = bundle.getString("KEY_POSITION3")
            str4 = bundle.getString("KEY_POSITION4")
            mode = bundle.getBoolean("KEY_POSITION5")
        }

        if(mode){
            binding.scorebutton1.text = "スライムの森"
            binding.scorebutton2.text = "Rダンジョン"
            binding.scorebutton3.visibility = View.INVISIBLE;
            binding.scorebutton4.visibility = View.INVISIBLE;
        }

        binding.scoretext1.text = str1
        binding.scoretext2.text = str2
        binding.scoretext3.text = str3
        binding.scoretext4.text = str4

        binding.scorebutton1.setOnClickListener {
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                fragmentManager.beginTransaction().remove(this).commit()
            }
            textchange(1)
        }

        binding.scorebutton2.setOnClickListener {
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                fragmentManager.beginTransaction().remove(this).commit()
            }
            textchange(2)
        }

        binding.scorebutton3.setOnClickListener {
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                fragmentManager.beginTransaction().remove(this).commit()
            }
            textchange(3)
        }

        binding.scorebutton4.setOnClickListener {
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                fragmentManager.beginTransaction().remove(this).commit()
            }
            textchange(4)
        }

        binding.backbutton2.setOnClickListener {
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                fragmentManager.beginTransaction().remove(this).commit()
            }
            back(it)
        }

        return binding.root
    }

    //ここから下コピペ推奨
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? scoreFragment.scoreListener//上記のリスナー名と同じにしてください。
        if (listener == null) {
            throw ClassCastException("$context must implement configListener")//同上
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun back(view: View) {
        listener?.backconfig()
    }

    fun textchange(int:Int) {
        listener?.textchange(int)
    }

}