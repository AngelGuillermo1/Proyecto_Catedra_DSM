// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/ui/AnswerResult.kt
package com.example.proyecto_catedra_dsm.ui

import com.example.proyecto_catedra_dsm.model.Pregunta
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnswerResult(
    val pregunta: Pregunta,
    val seleccion: String,
    val esCorrecta: Boolean
) : Parcelable
