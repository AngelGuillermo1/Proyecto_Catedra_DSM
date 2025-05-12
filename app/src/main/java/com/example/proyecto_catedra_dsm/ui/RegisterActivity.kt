// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/RegisterActivity.kt
package com.example.proyecto_catedra_dsm.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.db.DBHelper
import com.example.proyecto_catedra_dsm.model.Usuario
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = DBHelper(this)

        val etName     = findViewById<EditText>(R.id.etName)
        val etEmail    = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSubmit  = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val name  = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass  = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_fill_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val id = db.addUsuario(
                Usuario(
                    nombre        = name,
                    email         = email,
                    password      = pass,
                    fechaRegistro = fecha
                )
            )

            if (id > 0) {
                Toast.makeText(this, getString(R.string.msg_registered), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, getString(R.string.error_register), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
