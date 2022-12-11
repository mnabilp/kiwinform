package com.capstone.kiwinform.ui.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstone.kiwinform.R
import com.capstone.kiwinform.databinding.ActivityProfileBinding
import com.capstone.kiwinform.ui.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private var binding: ActivityProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        binding?.btnLogout?.setOnClickListener(this)
        binding?.toolbarProfile?.tvToolbarTitle?.text = getString(R.string.statistics)
        binding?.toolbarProfile?.btnToolbarBack?.setOnClickListener {
            super.onBackPressed()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_logout -> {
                firebaseAuth.signOut()
                // user is now signed out
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}