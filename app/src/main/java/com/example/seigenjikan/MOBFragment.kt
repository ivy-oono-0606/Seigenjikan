package com.example.seigenjikan

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seigenjikan.databinding.FragmentMOBBinding

class MOBFragment : Fragment() {
    private var _binding: FragmentMOBBinding? = null
    private val binding get() = _binding!!
    private lateinit var bindingM: MOBFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_m_o_b)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMOBBinding.inflate(inflater, container, false)
        binding.Next2ImageButton.setOnClickListener{
//            val myImage: ImageView = findViewById(R.id.MobImageView)
//            myImage.setImageResource(R.drawable.mobu)
//            val intent = Intent(activity, SubActivity::class.java)
//            startActivity(intent)
        }

        val inflateX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.9f, 0.87f)
        val inflateY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.9f, 0.87f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(binding.MobImageView, inflateX, inflateY).apply {
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
    }
