// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/MateriaListActivity.kt
package com.example.proyecto_catedra_dsm.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.db.DBHelper

class MateriaListActivity : AppCompatActivity() {
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materia_list)

        db = DBHelper(this)

        findViewById<Button>(R.id.btnNewMateria).setOnClickListener {

            startActivity(Intent(this, MateriaDetailActivity::class.java))
        }

        findViewById<RecyclerView>(R.id.rvMaterias).layoutManager =
            LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()

        val list = db.getAllMaterias(usuarioId = 1)
        findViewById<RecyclerView>(R.id.rvMaterias).adapter =
            MateriaAdapter(list,
                onEdit = { m ->
                    Intent(this, MateriaDetailActivity::class.java).apply {
                        putExtra("MATERIA_ID", m.id)
                        startActivity(this)
                    }
                },
                onQuiz = { m ->
                    Intent(this, QuizActivity::class.java).apply {
                        putExtra("MATERIA_ID", m.id)
                        startActivity(this)
                    }
                }
            )
    }
}
