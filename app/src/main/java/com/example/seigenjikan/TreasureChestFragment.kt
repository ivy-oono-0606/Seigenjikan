package com.example.seigenjikan

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seigenjikan.databinding.FragmentNPCBinding
import com.example.seigenjikan.databinding.FragmentTreasureChestBinding

class TreasureChestFragment : Fragment() {
    private var _binding: FragmentTreasureChestBinding? = null
    private val binding get() = _binding!!
    private var listener: TreasureChestListener? = null//イベントリスナー

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    interface TreasureChestListener {//上記と同名で定義
    fun onClickNext()//ここでアクティビティのメソッドに渡します。
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentTreasureChestBinding.inflate(inflater, container, false)

        binding.TreasureChestImageButton.setOnClickListener{
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                onClickNext(it)//下記のメソッドを呼びます。下をコピペした後ワンクリックリスナーなどで呼び出ししてください。
                fragmentManager.beginTransaction().remove(this).commit()
            }
        }
        return binding.root
    }

    //ここから下コピペ推奨
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? TreasureChestFragment.TreasureChestListener//上記のリスナー名と同じにしてください。
        if (listener == null) {
            throw ClassCastException("$context must implement TreasureChestListener")//同上
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun onClickNext(view: View) {
        listener?.onClickNext()
    }
}