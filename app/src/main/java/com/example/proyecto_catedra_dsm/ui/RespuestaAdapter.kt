// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/RespuestaAdapter.kt
package com.example.proyecto_catedra_dsm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_catedra_dsm.R
import com.example.proyecto_catedra_dsm.model.Respuesta

class RespuestaAdapter(
    private val items: List<Respuesta>,
    private val onEdit: (Respuesta) -> Unit,
    private val onDelete: (Respuesta) -> Unit
) : RecyclerView.Adapter<RespuestaAdapter.VH>() {

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvText    = view.findViewById<TextView>(R.id.tvRespuestaText)
        private val tvCorrect = view.findViewById<TextView>(R.id.tvRespuestaCorrect)

        fun bind(r: Respuesta) {
            tvText.text    = r.texto
            tvCorrect.text = if (r.esCorrecta) "✓" else "✗"
            itemView.setOnClickListener { onEdit(r) }
            itemView.setOnLongClickListener {
                onDelete(r)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_respuesta, parent, false)
        return VH(v)
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, pos: Int) = holder.bind(items[pos])
}
