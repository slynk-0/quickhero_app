package com.deamon.quickheroapp.main_ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deamon.quickheroapp.R
import com.deamon.quickheroapp.backend.FichaRPG
import com.deamon.quickheroapp.backend.GeradorFicha

private data class CharacterCardContent(
    val ficha: FichaRPG,
    val alinhamento: String,
    val gancho: String
)

private val initialCard = CharacterCardContent(
    ficha = FichaRPG(
        id = 0,
        nome = "Faerion",
        classe = "Mago",
        raca = "Elfo",
        atributoDestaque = "Inteligência"
    ),
    alinhamento = "Caótico e Bom",
    gancho = "Guarda o mapa de um tesouro indecifrável."
)

private val alinhamentos = listOf(
    "Leal e Bom",
    "Neutro e Bom",
    "Caótico e Bom",
    "Leal e Neutro",
    "Neutro Puro",
    "Caótico e Neutro"
)

private val ganchos = listOf(
    "Procura por um irmão desaparecido após um ataque à sua vila.",
    "Deve uma grande dívida de ouro a uma guilda de ladrões.",
    "Guarda o mapa de um tesouro indecifrável.",
    "Um amuleto antigo esquenta na presença de magia."
)

@Composable
fun HomeScreen() {
    var card by remember { mutableStateOf(initialCard) }
    var isFavorite by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    fun generateCharacter() {
        card = CharacterCardContent(
            ficha = GeradorFicha.gerarFichaAleatoria(),
            alinhamento = alinhamentos.random(),
            gancho = ganchos.random()
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.wallpaperdnd),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x990A0608))
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFB5092B))
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .height(58.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable(
                                role = Role.Button,
                                onClick = { isMenuExpanded = true }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(width = 24.dp, height = 20.dp)) {
                            val lineWidth = 2.dp.toPx()
                            for (index in 0..2) {
                                val y = size.height * index / 2f
                                drawLine(
                                    color = Color.White,
                                    start = androidx.compose.ui.geometry.Offset(0f, y),
                                    end = androidx.compose.ui.geometry.Offset(size.width, y),
                                    strokeWidth = lineWidth
                                )
                            }
                        }
                    }
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Gerar nova ficha") },
                            onClick = {
                                isMenuExpanded = false
                                generateCharacter()
                            }
                        )
                    }
                }

                Text(
                    text = "D&D Quick\nHero",
                    modifier = Modifier.weight(1f),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 19.sp,
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(
                            role = Role.Button,
                            onClick = { isFavorite = !isFavorite }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isFavorite) "♥" else "♡",
                        color = Color.White,
                        fontSize = 29.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Clique no dado e\ngere sua ficha!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    color = Color.White,
                    fontFamily = FontFamily.Serif,
                    fontSize = 28.sp,
                    lineHeight = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.82f)
                        .height(100.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(role = Role.Button, onClick = ::generateCharacter),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.baixados__1_),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0x22090012))
                    )
                    DiceIllustration(modifier = Modifier.size(width = 112.dp, height = 88.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))
                CharacterCard(card = card, modifier = Modifier.fillMaxWidth(0.90f))
            }
        }
    }
}

@Composable
private fun CharacterCard(card: CharacterCardContent, modifier: Modifier = Modifier) {
    val raceBonus = when (card.ficha.raca) {
        "Elfo" -> "+2 Agilidade"
        "Anão" -> "+2 Vitalidade"
        "Humano" -> "+1 em Todos os Atributos"
        "Tiefling" -> "+2 Carisma"
        else -> "Bônus racial por definir"
    }
    val attribute = when (card.ficha.atributoDestaque) {
        "Inteligência" -> "Mente Brilhante"
        else -> card.ficha.atributoDestaque
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF65412F))
            .drawWithContent {
                drawContent()
                drawRoundRect(
                    color = Color(0xFF493125),
                    style = Stroke(width = 4.dp.toPx()),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(18.dp.toPx())
                )
            }
            .padding(5.dp)
            .clip(RoundedCornerShape(14.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.grunge_paper_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x3370442E))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "🧝 ${card.ficha.nome.uppercase()}",
                color = Color(0xFFF2DFC7),
                fontFamily = FontFamily.Serif,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${card.ficha.raca} • ${card.ficha.classe} • ${card.alinhamento}",
                color = Color(0xFFF2DFC7),
                fontFamily = FontFamily.Serif,
                fontSize = 11.sp
            )
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                DetailLine("Atributo: $attribute")
                DetailLine("Bônus Racial: $raceBonus")
                DetailLine("Gancho: ${card.gancho}")
            }
        }
    }
}

@Composable
private fun DetailLine(text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = "✦",
            color = Color(0xFFF2DFC7),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            color = Color(0xFFF2DFC7),
            fontFamily = FontFamily.Serif,
            fontSize = 11.sp,
            lineHeight = 13.sp
        )
    }
}

@Composable
private fun DiceIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val lineWidth = 3.dp.toPx()
        val points = listOf(
            androidx.compose.ui.geometry.Offset(size.width * 0.50f, size.height * 0.04f),
            androidx.compose.ui.geometry.Offset(size.width * 0.87f, size.height * 0.23f),
            androidx.compose.ui.geometry.Offset(size.width * 0.95f, size.height * 0.63f),
            androidx.compose.ui.geometry.Offset(size.width * 0.62f, size.height * 0.96f),
            androidx.compose.ui.geometry.Offset(size.width * 0.29f, size.height * 0.91f),
            androidx.compose.ui.geometry.Offset(size.width * 0.05f, size.height * 0.56f),
            androidx.compose.ui.geometry.Offset(size.width * 0.17f, size.height * 0.22f)
        )
        val center = androidx.compose.ui.geometry.Offset(size.width * 0.50f, size.height * 0.53f)
        val outline = Path().apply {
            moveTo(points.first().x, points.first().y)
            points.drop(1).forEach { lineTo(it.x, it.y) }
            close()
        }
        drawPath(outline, Color.White, style = Stroke(width = lineWidth))
        points.forEachIndexed { index, point ->
            val next = points[(index + 2) % points.size]
            drawLine(Color.White, point, center, strokeWidth = lineWidth)
            if (index % 2 == 0) {
                drawLine(Color.White, point, next, strokeWidth = lineWidth)
            }
        }
    }
}
