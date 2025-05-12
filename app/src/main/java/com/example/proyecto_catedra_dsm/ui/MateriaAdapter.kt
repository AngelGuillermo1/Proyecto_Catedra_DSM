// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/MateriaAdapter.kt
package com.example.proyecto_catedra_dsm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.model.Materia

class MateriaAdapter(
    private val items: List<Materia>,
    private val onEdit: (Materia) -> Unit,
    private val onQuiz: (Materia) -> Unit
) : RecyclerView.Adapter<MateriaAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val tv = view.findViewById<TextView>(R.id.tvMateriaTitulo)
        private val btnEdit = view.findViewById<ImageButton>(R.id.btnEditMateria)
        private val btnQuiz = view.findViewById<ImageButton>(R.id.btnQuizMateria)

        fun bind(m: Materia) {
            tv.text = m.titulo
            btnEdit.setOnClickListener { onEdit(m) }
            btnQuiz.setOnClickListener { onQuiz(m) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_materia, parent, false)
        return VH(v)
    }
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, pos: Int) = holder.bind(items[pos])
}
