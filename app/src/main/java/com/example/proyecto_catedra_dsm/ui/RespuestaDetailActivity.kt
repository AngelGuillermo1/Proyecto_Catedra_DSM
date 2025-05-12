// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/RespuestaDetailActivity.kt
package com.example.proyecto_catedra_dsm.ui

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.db.DBHelper
import com.example.proyecto_catedra_dsm.model.Respuesta

class RespuestaDetailActivity : AppCompatActivity() {
    private lateinit var db: DBHelper
    private var preguntaId = 0
    private var respuestaId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respuesta_detail)

        db = DBHelper(this)
        preguntaId  = intent.getIntExtra("PREGUNTA_ID", 0)
        respuestaId = intent.getIntExtra("RESPUESTA_ID",  0)

        val etText    = findViewById<EditText>(R.id.etAnswerText)
        val cbCorrect = findViewById<CheckBox>(R.id.cbCorrecta)
        val btnSave   = findViewById<Button>(R.id.btnSaveAnswer)


        if (respuestaId > 0) {
            db.getRespuestaById(respuestaId)?.let { r ->
                etText.setText(r.texto)
                cbCorrect.isChecked = r.esCorrecta
            }
        }

        btnSave.setOnClickListener {
            val texto = etText.text.toString().trim()
            if (texto.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_fill_fields),
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val esCorr = cbCorrect.isChecked

            if (respuestaId > 0) {
                db.updateRespuesta(Respuesta(respuestaId, texto, preguntaId, esCorr))
                Toast.makeText(this, "Respuesta actualizada", Toast.LENGTH_SHORT).show()
            } else {
                db.addRespuesta(Respuesta(0, texto, preguntaId, esCorr))
                Toast.makeText(this, "Respuesta creada", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}
