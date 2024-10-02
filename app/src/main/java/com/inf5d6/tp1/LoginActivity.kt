package com.inf5d6.tp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialisation des vues
        emailEditText = findViewById(R.id.EmailAddress)
        passwordEditText = findViewById(R.id.Password)
        loginButton = findViewById(R.id.LoginButton)

        loginButton.setOnClickListener {
            authenticateUser(emailEditText.text.toString(), passwordEditText.text.toString())
        }
    }

    private fun authenticateUser(email: String, password: String) {
        val client = OkHttpClient()
        val url = "${MainActivity.SRVURL}/auth/token" // Utilisation de l'URL de l'API

        val jsonObject = JSONObject().apply {
            put("email", email)
            put("password", password)
        }

        val body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Erreur de réseau", e)
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Erreur de connexion", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.string()
                    responseData?.let {
                        val jsonResponse = JSONObject(it)
                        val token = jsonResponse.getString("token") // Assurez-vous que votre API renvoie un champ "token"
                        MainActivity.TOKEN = token // Stockez le jeton dans la variable globale
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    // Gestion de l'erreur si le code n'est pas 200
                    val errorResponse = response.body()?.string()
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Erreur d'authentification: $errorResponse", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}