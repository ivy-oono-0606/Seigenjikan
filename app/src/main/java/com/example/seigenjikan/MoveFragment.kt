package com.example.seigenjikan

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seigenjikan.databinding.FragmentMoveBinding

class MoveFragment : Fragment() {
    private var _binding: FragmentMoveBinding? = null
    private val binding get() = _binding!!
    private var listener: MoveListener? = null//イベントリスナー

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    interface MoveListener {//上記と同名で定義
        fun moveright()//ここでアクティビティのメソッドに渡します。
        fun moveleft()//ここでアクティビティのメソッドに渡します。
        fun moveforward()//ここでアクティビティのメソッドに渡します。
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentMoveBinding.inflate(inflater, container, false)

        binding.RightImageButton.setOnClickListener{
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                test2(it)//下記のメソッドを呼びます。下をコピペした後ワンクリックリスナーなどで呼び出ししてください。
                fragmentManager.beginTransaction().remove(this).commit()
            }
        }

        binding.LeftImageButton.setOnClickListener{
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                test3(it)//下記のメソッドを呼びます。下をコピペした後ワンクリックリスナーなどで呼び出ししてください。
                fragmentManager.beginTransaction().remove(this).commit()
            }
        }

        binding.UpImageButton.setOnClickListener{
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                test4(it)//下記のメソッドを呼びます。下をコピペした後ワンクリックリスナーなどで呼び出ししてください。
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

    fun test2(view: View) {
        listener?.moveright()
    }

    fun test3(view: View) {
        listener?.moveleft()
    }

    fun test4(view: View) {
        listener?.moveforward()
    }
}