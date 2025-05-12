// File: app/src/main/kotlin/com/example/proyecto_catedra_dsm/model/Respuesta.kt
package com.example.proyecto_catedra_dsm.model


data class Respuesta(
    var id: Int = 0,
    var texto: String = "",
    var idPregunta: Int = 0,
    var esCorrecta: Boolean = false
)
