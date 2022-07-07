package com.example.seigenjikan

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seigenjikan.databinding.FragmentMoveBinding
import com.example.seigenjikan.databinding.FragmentNPCBinding

class MoveFragment : Fragment() {
    private var _binding: FragmentMoveBinding? = null
    private val binding get() = _binding!!
    private var listener: MoveListener? = null//イベントリスナー

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    interface MoveListener {//上記と同名で定義
    fun onClickNext()//ここでアクティビティのメソッドに渡します。
//        fun onClickNext()//ここでアクティビティのメソッドに渡します。
//        fun onClickNext()//ここでアクティビティのメソッドに渡します。
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMoveBinding.inflate(inflater, container, false)

        binding.LeftImageButton.setOnClickListener{
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                onClickNext(it)//下記のメソッドを呼びます。下をコピペした後ワンクリックリスナーなどで呼び出ししてください。
                fragmentManager.beginTransaction().remove(this).commit()
            }
        }

        binding.UpImageButton.setOnClickListener{
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                onClickNext(it)//下記のメソッドを呼びます。下をコピペした後ワンクリックリスナーなどで呼び出ししてください。
                fragmentManager.beginTransaction().remove(this).commit()
            }
        }

        binding.RightImageButton.setOnClickListener{
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
        listener = context as? MoveFragment.MoveListener//上記のリスナー名と同じにしてください。
        if (listener == null) {
            throw ClassCastException("$context must implement MoveListener")//同上
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun onClickNext(view: View) {
        listener?.onClickNext()
//        listener?.onClickNext()
//        listener?.onClickNext()
    }
}