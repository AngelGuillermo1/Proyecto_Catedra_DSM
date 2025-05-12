// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/RespuestaListActivity.kt
package com.example.proyecto_catedra_dsm.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.db.DBHelper

class RespuestaListActivity : AppCompatActivity() {
    private lateinit var db: DBHelper
    private var preguntaId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respuesta_list)

        setSupportActionBar(findViewById(R.id.toolbar_respuestas))
        db = DBHelper(this)
        preguntaId = intent.getIntExtra("PREGUNTA_ID", 0)

        findViewById<Button>(R.id.btnNewRespuesta).setOnClickListener {
            startActivity(Intent(this, RespuestaDetailActivity::class.java).apply {
                putExtra("PREGUNTA_ID", preguntaId)
            })
        }

        val rv = findViewById<RecyclerView>(R.id.rvRespuestas)
        rv.layoutManager = LinearLayoutManager(this)
        load()
    }

    private fun load() {
        val list = db.getRespuestasPorPregunta(preguntaId)
        findViewById<RecyclerView>(R.id.rvRespuestas).adapter =
            RespuestaAdapter(list,
                onEdit = { r ->
                    startActivity(Intent(this, RespuestaDetailActivity::class.java).apply {
                        putExtra("RESPUESTA_ID", r.id)
                        putExtra("PREGUNTA_ID", preguntaId)
                    })
                },
                onDelete = { r ->
                    db.deleteRespuesta(r.id)
                    load()
                }
            )
    }
}

