package com.kris.indihealthpretest.register

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kris.indihealthpretest.databinding.ActivityRegisterBinding
import com.kris.indihealthpretest.login.LoginActivity
import com.kris.indihealthpretest.profile.ProfileActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating account in....")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        initView()
    }

    private fun initView() {

        binding.btnRegister.setOnClickListener {
            validateData()
        }
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun validateData() {

        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error = "Invalid email format"
        } else if (TextUtils.isEmpty(password)){
            binding.etPassword.error = "Please enter correctly password"
        } else if (password.length <6 ){
            binding.etPassword.error = "Password must atleast 6 character"
        } else {
            firebaseSignup()
        }

    }

    private fun firebaseSignup() {
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"Account created with email $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this,"SignUp Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}