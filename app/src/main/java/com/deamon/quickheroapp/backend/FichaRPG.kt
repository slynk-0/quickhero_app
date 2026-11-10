package com.deamon.quickheroapp.backend

data class FichaRPG(
    val id: Int,
    val nome: String,
    val classe: String,
    val raca: String,
    val atributoDestaque: String
)

data class DadosRaca(
    val nome: String,
    val bonus: String,
    val prefixos: List<String>,
    val sufixos: List<String>
)
