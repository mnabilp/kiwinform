package com.capstone.kiwinform.ui.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.capstone.kiwinform.R
import com.capstone.kiwinform.databinding.ActivityRegisterBinding
import com.capstone.kiwinform.ui.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()

        binding.toolbarRegister.tvToolbarTitle.text = getString(R.string.title_register)
        binding.toolbarRegister.btnToolbarBack.setOnClickListener {
            super.onBackPressed()
        }
        binding.btnRegisterConfirm.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register_confirm -> {
                val email = binding.etRegisterInputEmail.text.toString()
                val fullName = binding.etRegisterInputName.text.toString()
                val password = binding.etRegisterInputPassword.text.toString()
                val confirmPassword = binding.etRegisterConfirmPassword.text.toString()

                if (email.isNotEmpty() && fullName.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    if (password == confirmPassword) {

                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this, "Account with that email already exists",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "The password doesn't match", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this, "All fields are required to be filled", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}