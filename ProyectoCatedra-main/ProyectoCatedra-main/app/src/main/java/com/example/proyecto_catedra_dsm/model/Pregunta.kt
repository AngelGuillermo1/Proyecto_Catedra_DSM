// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/model/Pregunta.kt
package com.example.proyecto_catedra_dsm.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pregunta(
    var id: Int = 0,
    var texto: String = "",
    var idMateria: Int = 0
) : Parcelable
