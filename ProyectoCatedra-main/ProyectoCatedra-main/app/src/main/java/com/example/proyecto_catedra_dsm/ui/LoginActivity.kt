// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/LoginActivity.kt
package com.example.proyecto_catedra_dsm.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.db.DBHelper

class LoginActivity : AppCompatActivity() {
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = DBHelper(this)

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass  = etPassword.text.toString().trim()
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_fill_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val user = db.getUsuarioByEmail(email)
            if (user != null && user.password == pass) {
                startActivity(Intent(this, MateriaListActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, getString(R.string.error_invalid_credentials), Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
