package com.inf5d6.tp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.EmailAddress)
        password = findViewById(R.id.Password)
        loginButton = findViewById(R.id.LoginButton)

        loginButton.setOnClickListener {
            authentication(email.text.toString(), password.text.toString())
        }
    }

    private fun authentication(email: String, password: String) {
        val url = "https://pokemonsapi.herokuapp.com/auth/token"
        val queue: RequestQueue = Volley.newRequestQueue(this)

        val body = JSONObject().apply {
            put("email", email)
            put("password", password)
        }

        val postRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            body,
            { response ->
                val token = response.getString("token")
                MainActivity.TOKEN = token
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            },
            { error ->
                Log.d("Error.Response", error.message.toString())
            }
        )
        queue.add(postRequest)
    }
}
