// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/QuizActivity.kt
package com.example.proyecto_catedra_dsm.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.db.DBHelper
import com.example.proyecto_catedra_dsm.model.Pregunta
import com.google.android.material.progressindicator.LinearProgressIndicator

class QuizActivity : AppCompatActivity() {
    private lateinit var db: DBHelper
    private var materiaId = 0
    private lateinit var preguntas: List<Pregunta>
    private var index = 0


    private val respuestasUser = mutableListOf<AnswerResult>()


    private lateinit var tvQuestion: TextView
    private lateinit var rgOptions: RadioGroup
    private lateinit var btnNext: Button
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: LinearProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        db = DBHelper(this)
        materiaId = intent.getIntExtra("MATERIA_ID", 0)
        preguntas = db.getPreguntasPorMateria(materiaId)


        tvQuestion  = findViewById(R.id.tvQuestion)
        rgOptions   = findViewById(R.id.rgOptions)
        btnNext     = findViewById(R.id.btnNext)
        tvProgress  = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressQuiz)


        progressBar.max = preguntas.size

        btnNext.setOnClickListener {
            val selId = rgOptions.checkedRadioButtonId
            if (selId == -1) {
                Toast.makeText(this, getString(R.string.error_select_option), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val rb = findViewById<RadioButton>(selId)
            val textoSel = rb.text.toString()
            val esCorr = rb.tag == true


            respuestasUser.add(
                AnswerResult(
                    pregunta = preguntas[index],
                    seleccion = textoSel,
                    esCorrecta = esCorr
                )
            )

            index++
            if (index < preguntas.size) {
                showQuestion(index)
            } else {

                Intent(this, ResultActivity::class.java).apply {
                    putParcelableArrayListExtra("RESULTS", ArrayList(respuestasUser))
                    startActivity(this)
                    finish()
                }
            }
        }


        showQuestion(0)
    }

    private fun showQuestion(i: Int) {
        val q = preguntas[i]
        tvQuestion.text = q.texto


        rgOptions.removeAllViews()
        db.getRespuestasPorPregunta(q.id).forEach { r ->
            RadioButton(this).apply {
                text = r.texto
                tag = r.esCorrecta
                // opcional: padding para mejor touch target
                setPadding(16, 16, 16, 16)
                rgOptions.addView(this)
            }
        }


        tvProgress.text = getString(R.string.quiz_progress_format, i + 1, preguntas.size)
        progressBar.progress = i + 1


        btnNext.text = if (i == preguntas.size - 1)
            getString(R.string.btn_finish)
        else
            getString(R.string.btn_next)
    }
}
