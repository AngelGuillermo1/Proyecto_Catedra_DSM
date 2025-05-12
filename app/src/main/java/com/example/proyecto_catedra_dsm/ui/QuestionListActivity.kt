// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/QuestionListActivity.kt
package com.example.proyecto_catedra_dsm.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.db.DBHelper

class QuestionListActivity : AppCompatActivity() {
    private lateinit var db: DBHelper
    private var materiaId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)

        db = DBHelper(this)
        materiaId = intent.getIntExtra("MATERIA_ID", 0)

        findViewById<Button>(R.id.btnNewPregunta).setOnClickListener {
            startActivity(Intent(this, QuestionDetailActivity::class.java).apply {
                putExtra("MATERIA_ID", materiaId)
            })
        }

        findViewById<RecyclerView>(R.id.rvPreguntas).apply {
            layoutManager = LinearLayoutManager(this@QuestionListActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        load()
    }

    private fun load() {
        val preguntas = db.getPreguntasPorMateria(materiaId)
        findViewById<RecyclerView>(R.id.rvPreguntas).adapter =
            PreguntaAdapter(
                preguntas,
                onEdit = { p ->
                    startActivity(Intent(this, QuestionDetailActivity::class.java).apply {
                        putExtra("MATERIA_ID", materiaId)
                        putExtra("PREGUNTA_ID", p.id)
                    })
                },
                onDelete = { p ->
                    db.deletePregunta(p.id)
                    load()
                }
            )
    }
}
