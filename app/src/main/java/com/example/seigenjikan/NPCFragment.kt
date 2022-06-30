package com.example.seigenjikan

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.seigenjikan.databinding.FragmentNPCBinding


class NPCFragment : Fragment() {
    private var _binding: FragmentNPCBinding? = null
    private val binding get() = _binding!!
    private var listener: OnboardSignUpTermsOfServiceListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_n_p_c)
    }

    interface OnboardSignUpTermsOfServiceListener {
        fun onClickNext()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentNPCBinding.inflate(inflater, container, false)
        var charaimage:String? = ""
        var backimage:String? = ""
        // Bundleを取得する
        val bundle = arguments
        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            charaimage = bundle.getString("KEY_POSITION")
            backimage = bundle.getString("KEY_POSITION2")
        }
        //packageName取得
        val packageName = BuildConfig.APPLICATION_ID

        var imageId = resources.getIdentifier(charaimage, "drawable", packageName) //リソースIDのを取得
        binding.NpcImageView.setImageResource(imageId)
        imageId = resources.getIdentifier(backimage, "drawable", packageName) //リソースIDのを取得
        binding.HaikeiImageView.setImageResource(imageId)

        binding.NextImageButton.setOnClickListener{
//            val myImage: ImageView = findViewById(R.id.NpcImageView)
//            myImage.setImageResource(R.drawable.mobu)
//            val intent = Intent(activity, SubActivity::class.java)
//            startActivity(intent)
            val fragmentManager = fragmentManager
            if(fragmentManager != null) {
                onClickNext(it)
                fragmentManager.beginTransaction().remove(this).commit()
            }
        }

        val inflateX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.9f, 0.87f)
        val inflateY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.9f, 0.87f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.NpcImageView, inflateX, inflateY).apply {
            duration = 1500
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }
        animator.start()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnboardSignUpTermsOfServiceListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnboardSignUpTermsOfServiceListener")
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