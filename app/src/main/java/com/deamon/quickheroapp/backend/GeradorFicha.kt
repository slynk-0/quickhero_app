package com.deamon.quickheroapp.backend

object GeradorFicha {
    private val classes = listOf(
        "Guerreiro", "Mago", "Ladino", "Clérigo",
        "Bárbaro", "Bardo", "Paladino", "Patrulheiro"
    )

    private val alinhamentos = listOf(
        "Leal e Bom", "Neutro e Bom", "Caótico e Bom",
        "Leal e Neutro", "Neutro Puro", "Caótico e Neutro"
    )

    private val ganchos = listOf(
        "Possui um amuleto antigo que esquenta na presença de magia.",
        "Procura por um irmão desaparecido após um ataque a sua vila.",
        "Coleciona dentes e garras de criaturas derrotadas.",
        "Tem pavor incontrolável de galinhas e aves pequenas.",
        "Deve uma grande dívida de ouro a uma guilda de ladrões local.",
        "Fala sozinho em momentos de tensão como se consultasse um mentor invisível.",
        "Esconde uma cicatriz mágica no rosto que brilha quando chove.",
        "Recusa-se terminantemente a lutar antes de tomar um chá de ervas.",
        "Sonha repetidamente com uma ruína submersa que nunca visitou.",
        "Guarda o mapa de um tesouro que ninguém consegue decifrar."
    )

    // Dicionário de Raças com Bônus e Sílabas para Nomes
    private val racas = listOf(
        DadosRaca(
            nome = "Elfo",
            bonus = "+2 Agilidade",
            prefixos = listOf("Eld", "Gal", "Aer", "Faer", "Thal", "Syl"),
            sufixos = listOf("ael", "ith", "ion", "wen", "ora", "mar")
        ),
        DadosRaca(
            nome = "Anão",
            bonus = "+2 Vitalidade",
            prefixos = listOf("Thor", "Bal", "Kaz", "Dain", "Grim", "Mor"),
            sufixos = listOf("ak", "grim", "dur", "und", "kahr", "din")
        ),
        DadosRaca(
            nome = "Humano",
            bonus = "+1 em Todos os Atributos",
            prefixos = listOf("Ed", "Rod", "Val", "Art", "Marc", "Gor"),
            sufixos = listOf("rick", "ian", "us", "ond", "old", "an")
        ),
        DadosRaca(
            nome = "Tiefling",
            bonus = "+2 Carisma",
            prefixos = listOf("Aka", "Mor", "Zar", "Kair", "Val"),
            sufixos = listOf("mos", "thos", "rius", "lech", "us")
        )
    )

    fun gerarFichaAleatoria(): FichaRPG {
        val classe = classes.random()
        val atributoDestaque = when (classe) {
            "Guerreiro", "Bárbaro" -> "Força"
            "Mago" -> "Inteligência"
            "Ladino", "Patrulheiro" -> "Agilidade"
            "Clérigo" -> "Sabedoria"
            "Bardo", "Paladino" -> "Carisma"
            else -> error("A classe $classe não possui um atributo definido.")
        }

        val raca = racas.random()
        val nome = raca.prefixos.random() + raca.sufixos.random()

        return FichaRPG(
            id = kotlin.random.Random.nextInt(1, Int.MAX_VALUE),
            nome = nome,
            classe = classe,
            raca = raca.nome,
            atributoDestaque = atributoDestaque
        )
    }

}
