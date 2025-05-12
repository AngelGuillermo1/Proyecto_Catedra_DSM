// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/QuestionDetailActivity.kt
package com.example.proyecto_catedra_dsm.ui

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.db.DBHelper
import com.example.proyecto_catedra_dsm.model.Pregunta
import com.example.proyecto_catedra_dsm.model.Respuesta

class QuestionDetailActivity : AppCompatActivity() {
    private lateinit var db: DBHelper
    private var materiaId = 0
    private var preguntaId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)

        db = DBHelper(this)
        materiaId = intent.getIntExtra("MATERIA_ID", 0)
        preguntaId = intent.getIntExtra("PREGUNTA_ID", 0)

        val etTexto: EditText = findViewById(R.id.etPreguntaText)
        val respuestas = listOf(
            findViewById<EditText>(R.id.etResp1),
            findViewById<EditText>(R.id.etResp2),
            findViewById<EditText>(R.id.etResp3)
        )
        val radios = listOf(
            findViewById<RadioButton>(R.id.rb1),
            findViewById<RadioButton>(R.id.rb2),
            findViewById<RadioButton>(R.id.rb3)
        )
        val btnSave: Button = findViewById(R.id.btnSavePregunta)

        // Si la pregunta ya existe, cargar sus datos
        if (preguntaId > 0) {
            db.getPreguntasPorMateria(materiaId)
                .firstOrNull { it.id == preguntaId }
                ?.also { pregunta ->
                    etTexto.setText(pregunta.texto)
                    db.getRespuestasPorPregunta(preguntaId).forEachIndexed { i, r ->
                        respuestas[i].setText(r.texto)
                        radios[i].isChecked = r.esCorrecta
                    }
                }
        }

        btnSave.setOnClickListener {
            val texto = etTexto.text.toString().trim()
            if (texto.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_fill_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val listRes = respuestas.map { it.text.toString().trim() }
            if (listRes.any { it.isEmpty() }) {
                Toast.makeText(this, getString(R.string.error_fill_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val correctIdx = radios.indexOfFirst { it.isChecked }
            if (correctIdx == -1) {
                Toast.makeText(this, getString(R.string.error_select_correct), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (preguntaId > 0) {

                db.updatePregunta(Pregunta(preguntaId, texto, materiaId))

                db.deleteRespuestasPorPregunta(preguntaId)
            } else {

                preguntaId = db.addPregunta(Pregunta(0, texto, materiaId)).toInt()
            }


            listRes.forEachIndexed { i, respText ->
                db.addRespuesta(Respuesta(0, respText, preguntaId, i == correctIdx))
            }

            Toast.makeText(this, getString(R.string.msg_saved), Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
