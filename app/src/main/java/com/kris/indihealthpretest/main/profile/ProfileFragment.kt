package com.kris.indihealthpretest.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.kris.indihealthpretest.R
import com.kris.indihealthpretest.databinding.FragmentProfileBinding
import com.kris.indihealthpretest.login.LoginActivity


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null){
            val email = firebaseUser.email
            val userName = firebaseUser.displayName
            binding.tvUserName.text = userName
            binding.tvEmail.text = email

        } else {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        initView()
    }

    private fun initView() {
        binding.ivLogOut.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }


}