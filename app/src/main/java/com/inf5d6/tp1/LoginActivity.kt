package com.inf5d6.tp1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class LoginActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.EmailAddress)
        password = findViewById(R.id.Password)
        loginButton = findViewById(R.id.LoginButton)
        loginButton.setOnClickListener {
            loginViewModel.authenticate(email.text.toString(), password.text.toString())
        }
        loginViewModel.loginSuccess.observe(this, Observer { success ->
            if (success) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        })
        loginViewModel.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })
    }
}
