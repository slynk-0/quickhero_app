package com.deamon.quickheroapp.backend

data class FichaRPG(
    val id: Int,
    val nome: String,
    val classe: String,
    val raca: String,
    val alinhamento: String,
    val atributo: String,
    val bonusRacial: String,
    val gancho: String
)

data class DadosRaca(
    val nome: String,
    val bonus: String,
    val prefixos: List<String>,
    val sufixos: List<String>
)
