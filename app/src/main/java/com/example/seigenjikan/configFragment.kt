package com.example.seigenjikan

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.seigenjikan.databinding.FragmentConfigBinding
import com.example.seigenjikan.databinding.FragmentMoveBinding

class configFragment : Fragment() {
    private var _binding:FragmentConfigBinding? = null
    private val binding get() = _binding!!
    private var listener:configListener?= null//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    interface configListener {//上記と同名で定義
    fun backconfig()//ここでアクティビティのメソッドに渡します。
    fun retire()
    fun SEconfig(bool:Boolean)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentConfigBinding.inflate(inflater, container, false)

        val bundle = arguments

        var SE:Boolean? = true

        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            SE = bundle.getBoolean("KEY_POSITION")
            binding.toggleButton.isChecked = SE
        }


        binding.retirebutton.setOnClickListener {
            retire(it)
        }

        binding.backbutton.setOnClickListener {
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                fragmentManager.beginTransaction().remove(this).commit()
            }
            back(it)
        }

        binding.toggleButton.setOnCheckedChangeListener { buttonView, isChecked ->
            // ON/OFFの状態(isChecked)をToastで表示
            SEconfig(isChecked)
            println(isChecked.toString())
        }
        return binding.root
    }

    //ここから下コピペ推奨
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? configListener//上記のリスナー名と同じにしてください。
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

    fun retire(view: View) {
        listener?.retire()
    }

    fun SEconfig(isChecked:Boolean) {
        listener?.SEconfig(isChecked)
    }
}