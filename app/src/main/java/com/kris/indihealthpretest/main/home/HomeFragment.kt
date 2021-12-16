package com.kris.indihealthpretest.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.kris.indihealthpretest.databinding.FragmentHomeBinding
import com.kris.indihealthpretest.join.JoinActivity
import com.kris.indihealthpretest.login.LoginActivity
import com.kris.indihealthpretest.main.home.adapter.HomeAdapter
import com.kris.indihealthpretest.models.dataClass.Category
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetView
import java.net.URL


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        setupAdapter()
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        val startForResult =registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result->

        }

        binding.btnJoin.setOnClickListener {
           startForResult.launch(Intent(requireContext(), JoinActivity::class.java))
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            val email = firebaseUser.email
            val userName = firebaseUser.displayName
            binding.tvUserName.text = email

        }
    }

    private fun setupAdapter(){

        val programList = mutableListOf(
            Category("Hipertensi","Dr. Deni"),
            Category("Flu","Dr. Reni"),
            Category("Stroke","Dr. Thomas"),
            Category("Covid-19","Dr. Indra"),
            Category("Mental","Dr Okto")
        )

        val adapter = HomeAdapter(programList)

        binding.rvRoom.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvRoom.adapter = adapter
    }


}