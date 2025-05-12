// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/MateriaDetailActivity.kt
package com.example.proyecto_catedra_dsm.ui

import android.content.Intent
import android.os.Bundle
import android.view.View                     // <-- Import necesario
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.db.DBHelper
import com.example.proyecto_catedra_dsm.model.Materia

class MateriaDetailActivity : AppCompatActivity() {
    private lateinit var db: DBHelper
    private var materiaId: Int = 0
    private val currentUserId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materia_detail)

        db = DBHelper(this)
        materiaId = intent.getIntExtra("MATERIA_ID", 0)

        val etTitulo       = findViewById<EditText>(R.id.etTitulo)
        val etDescripcion  = findViewById<EditText>(R.id.etDescripcion)
        val btnSave        = findViewById<Button>(R.id.btnSaveMateria)
        val btnDelete      = findViewById<Button>(R.id.btnDeleteMateria)
        val btnManagePreg  = findViewById<Button>(R.id.btnManagePreguntas)


        if (materiaId > 0) {
            db.getAllMaterias(currentUserId)
                .firstOrNull { it.id == materiaId }
                ?.let {
                    etTitulo.setText(it.titulo)
                    etDescripcion.setText(it.descripcion)
                }
            btnDelete.visibility     = View.VISIBLE
            btnManagePreg.visibility = View.VISIBLE
        } else {

            btnDelete.visibility     = View.GONE
            btnManagePreg.visibility = View.GONE
        }


        btnSave.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            val desc   = etDescripcion.text.toString().trim()
            if (titulo.isEmpty()) {
                Toast.makeText(this, "El título es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (materiaId > 0) {
                db.updateMateria(Materia(materiaId, titulo, desc, currentUserId))
                Toast.makeText(this, "Materia actualizada", Toast.LENGTH_SHORT).show()
            } else {
                val newId = db.addMateria(Materia(0, titulo, desc, currentUserId))
                if (newId > 0) {
                    materiaId = newId.toInt()
                    Toast.makeText(this, "Materia creada", Toast.LENGTH_SHORT).show()

                    btnDelete.visibility     = View.VISIBLE
                    btnManagePreg.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this, "Error al crear materia", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            finish()
        }


        btnDelete.setOnClickListener {
            if (materiaId > 0) {
                db.deleteMateria(materiaId)
                Toast.makeText(this, "Materia eliminada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }


        btnManagePreg.setOnClickListener {
            startActivity(
                Intent(this, QuestionListActivity::class.java)
                    .putExtra("MATERIA_ID", materiaId)
            )
        }
    }
}
