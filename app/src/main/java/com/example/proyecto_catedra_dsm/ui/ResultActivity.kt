// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/ResultActivity.kt
package com.example.proyecto_catedra_dsm.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_catedra_dsm.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        @Suppress("DEPRECATION")
        val results = intent.getParcelableArrayListExtra<AnswerResult>("RESULTS") ?: return


        val total = results.size
        val correctCount = results.count { it.esCorrecta }
        val percentage = (correctCount * 100.0 / total).toInt()


        findViewById<TextView>(R.id.tvScore).text =
            getString(R.string.msg_score, correctCount, total)
        findViewById<TextView>(R.id.tvGrade).text =
            "$percentage%"


        val container = findViewById<LinearLayout>(R.id.llResults)

        results.forEach { ar ->

            TextView(this).apply {
                text = getString(R.string.lbl_question_placeholder, ar.pregunta.texto)
                setPadding(0, 8, 0, 4)
                textSize = 16f
                setTextColor(resources.getColor(R.color.textPrimary, null))
            }.also(container::addView)


            val label = if (ar.esCorrecta) getString(R.string.correct) else getString(R.string.incorrect)
            TextView(this).apply {
                text = getString(R.string.lbl_answer_placeholder, ar.seleccion, label)
                setPadding(16, 0, 0, 12)
                textSize = 14f
                setTextColor(
                    if (ar.esCorrecta)
                        resources.getColor(R.color.success, null)
                    else
                        resources.getColor(R.color.error, null)
                )
            }.also(container::addView)
        }
    }
}

