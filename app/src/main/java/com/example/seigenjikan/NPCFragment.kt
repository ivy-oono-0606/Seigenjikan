package com.example.seigenjikan

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.seigenjikan.databinding.FragmentNPCBinding


class NPCFragment : Fragment() {
    private var _binding: FragmentNPCBinding? = null
    private val binding get() = _binding!!
    private lateinit var bindingA: NPCFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_n_p_c)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNPCBinding.inflate(inflater, container, false)
        binding.NextImageButton.setOnClickListener{
//            val myImage: ImageView = findViewById(R.id.NpcImageView)
//            myImage.setImageResource(R.drawable.mobu)
//            val intent = Intent(activity, SubActivity::class.java)
//            startActivity(intent)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}