// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/PreguntaAdapter.kt
package com.example.proyecto_catedra_dsm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.model.Pregunta

class PreguntaAdapter(
    private val items: List<Pregunta>,
    private val onEdit: (Pregunta) -> Unit,
    private val onDelete: (Pregunta) -> Unit
) : RecyclerView.Adapter<PreguntaAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val tv       = view.findViewById<TextView>(R.id.tvPreguntaTexto)
        private val btnEdit  = view.findViewById<ImageButton>(R.id.btnEditPregunta)
        private val btnDel   = view.findViewById<ImageButton>(R.id.btnDeletePregunta)

        fun bind(p: Pregunta) {
            tv.text = p.texto
            btnEdit.setOnClickListener { onEdit(p) }
            btnDel.setOnClickListener  { onDelete(p) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pregunta, parent, false)
        return VH(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(items[position])
}
