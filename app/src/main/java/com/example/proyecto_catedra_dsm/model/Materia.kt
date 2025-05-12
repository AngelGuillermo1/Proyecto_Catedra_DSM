// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/model/Materia.kt
package com.example.proyecto_catedra_dsm.model


data class Materia(
    var id: Int = 0,
    var titulo: String = "",
    var descripcion: String? = null,
    var idUsuario: Int = 0,
    var fechaCreacion: String = ""
)
