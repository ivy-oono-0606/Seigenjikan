package com.example.seigenjikan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.seigenjikan.databinding.ActivityMainBinding
import com.example.seigenjikan.databinding.ActivitySubBinding
import com.example.seigenjikan.databinding.FragmentNPCBinding

class NPCFragment : Fragment() {
    private var _binding: FragmentNPCBinding? = null
    private val binding get() = _binding!!
    private lateinit var bindingA: NPCFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNPCBinding.inflate(inflater, container, false)
        binding.NextImageButton.setOnClickListener{
            val intent = Intent(activity, SubActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}