package com.example.proyecto_catedra_dsm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_catedra_dsm.db.HelperDB

class Control_Preguntas : AppCompatActivity() {
    private lateinit var dbHelper: HelperDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_preguntas)

        dbHelper = HelperDB(this)

        val materiaInput = findViewById<EditText>(R.id.materiaInput)
        val preguntaInput = findViewById<EditText>(R.id.preguntaInput)
        val respuestaInput = findViewById<EditText>(R.id.respuestaInput)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val showButton = findViewById<Button>(R.id.showButton)

        saveButton.setOnClickListener {
            val materia = materiaInput.text.toString()
            val pregunta = preguntaInput.text.toString()
            val respuesta = respuestaInput.text.toString()

            if (materia.isNotEmpty() && pregunta.isNotEmpty() && respuesta.isNotEmpty()) {
                val rowsUpdated = dbHelper.updatePregunta(materia, pregunta, respuesta)
                if (rowsUpdated > 0) {
                    Toast.makeText(this, "Pregunta actualizada correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    val id = dbHelper.insertPregunta(materia, pregunta, respuesta)
                    if (id != -1L) {
                        Toast.makeText(this, "Pregunta guardada con ID: $id", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error al guardar la pregunta", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        showButton.setOnClickListener {
            val preguntas = dbHelper.getAllPreguntas()
            preguntas.forEach { pregunta ->
                Toast.makeText(this, pregunta.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}