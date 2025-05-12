// File: Usuario.kt
package com.example.proyecto_catedra_dsm.model



data class Usuario(
    var id: Int = 0,
    var nombre: String = "",
    var email: String = "",
    var password: String = "",
    var fechaRegistro: String = ""
)
