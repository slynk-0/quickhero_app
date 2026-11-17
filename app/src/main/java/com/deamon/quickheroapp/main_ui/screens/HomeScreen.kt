package com.deamon.quickheroapp.main_ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deamon.quickheroapp.backend.FichaRPG
import com.deamon.quickheroapp.backend.GeradorFicha
import com.deamon.quickheroapp.R

@Composable
fun HomeScreen(
    onNavigateToDetails: (FichaRPG) -> Unit = {},
    onNavigateToFavorites: () -> Unit = {}
) {
    var fichaAtual by remember { mutableStateOf<FichaRPG?>(null) }
    var rotacaoDado by remember { mutableFloatStateOf(0f) }

    val anguloAnimado by animateFloatAsState(
        targetValue = rotacaoDado,
        animationSpec = tween(durationMillis = 450),
        label = "rotacao_d20"
    )

    Scaffold(
        topBar = {
            TopBarQuickHero(
                onMenuClick = { /* Ação futura do drawer/menu */ },
                onFavoritesClick = onNavigateToFavorites
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 1. WALLPAPER DE FUNDO (baixados__2_) com ajuste de contraste, brilho e saturação
            Image(
                painter = painterResource(id = R.drawable.baixados__2_),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(
                    criarColorMatrix(
                        contrast = 1.08f,
                        brightness = -12f,
                        saturation = 1.15f
                    )
                )
            )

            // Camada atmosférica quente/escura para manter o contraste fiel ao Figma
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x4D2A0808), // Topo levemente avermelhado escuro
                                Color(0x331A0B0B), // Centro mais aberto para o dragão/castelo
                                Color(0x730D0606)  // Base mais escura para destacar a ficha
                            )
                        )
                    )
            )

            // 2. CONTEÚDO PRINCIPAL (Espaçamento fiel ao protótipo)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(22.dp))

                // Título com brilho dourado/âmbar e contorno fiel ao Figma
                TituloComBrilhoDourado(texto = "Clique no dado e\ngere sua ficha!")

                Spacer(modifier = Modifier.height(20.dp))

                // 3. BOTÃO DO DADO (baixados__1_ + whitedice)
                BotaoDadoD20(
                    angulo = anguloAnimado,
                    onClick = {
                        rotacaoDado += 360f
                        fichaAtual = GeradorFicha.gerarFichaAleatoria()
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 4. CARD DA FICHA (Pergaminho com moldura marrom escura)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 24.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = fichaAtual != null,
                        enter = fadeIn(tween(300)) + slideInVertically(
                            initialOffsetY = { it / 6 },
                            animationSpec = tween(300)
                        )
                    ) {
                        fichaAtual?.let { ficha ->
                            CardPergaminhoFigma(
                                ficha = ficha,
                                onClick = { onNavigateToDetails(ficha) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBarQuickHero(
    onMenuClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    // Gradiente horizontal sutil do vermelho carmim (#A50722) ao vermelho coral (#C8322B)
    val gradienteTopBar = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFA50722),
            Color(0xFFB31425),
            Color(0xFFC8322B)
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(76.dp)
            .background(gradienteTopBar)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.White,
                modifier = Modifier.size(34.dp)
            )
        }

        Text(
            text = "D&D Quick\nHero",
            color = Color.White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 26.sp,
            textAlign = TextAlign.Center
        )

        IconButton(onClick = onFavoritesClick) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Fichas Favoritas",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun TituloComBrilhoDourado(texto: String) {
    Box(contentAlignment = Alignment.Center) {
        // Camada traseira: Contorno dourado/âmbar + sombra quente
        Text(
            text = texto,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 35.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color(0xFFD49B27),
                drawStyle = Stroke(
                    width = 4.5f,
                    join = StrokeJoin.Round
                ),
                shadow = Shadow(
                    color = Color(0xCC5E1906),
                    offset = Offset(0f, 3f),
                    blurRadius = 10f
                )
            )
        )

        // Camada frontal: Texto principal branco nítido
        Text(
            text = texto,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 35.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BotaoDadoD20(
    angulo: Float,
    onClick: () -> Unit
) {
    val shapeDado = RoundedCornerShape(30.dp)

    Box(
        modifier = Modifier
            .width(248.dp)
            .height(142.dp)
            .shadow(elevation = 12.dp, shape = shapeDado)
            .clip(shapeDado)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Color.White),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        // Fundo psicodélico carmim/magenta (baixados__1_)
        Image(
            painter = painterResource(id = R.drawable.baixados__1_),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(
                criarColorMatrix(
                    contrast = 1.18f,
                    brightness = -6f,
                    saturation = 1.25f
                )
            )
        )

        // Vinheta sutil nas bordas internas do botão
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x40120008)
                        )
                    )
                )
        )

        // Ícone D20 branco (whitedice)
        Image(
            painter = painterResource(id = R.drawable.whitedice),
            contentDescription = "Rolar Dado D20",
            modifier = Modifier
                .size(104.dp)
                .rotate(angulo),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun CardPergaminhoFigma(
    ficha: FichaRPG,
    onClick: () -> Unit
) {
    val shapeCard = RoundedCornerShape(24.dp)
    val corMolduraMarrom = Color(0xFF3E231D) // Borda externa espessa do protótipo

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .defaultMinSize(minHeight = 310.dp)
            .shadow(elevation = 14.dp, shape = shapeCard)
            .clip(shapeCard)
            .clickable(onClick = onClick),
        shape = shapeCard,
        border = BorderStroke(width = 9.dp, color = corMolduraMarrom),
        color = Color(0xFF5A392B)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Textura de pergaminho envelhecido (grunge_paper_background)
            Image(
                painter = painterResource(id = R.drawable.grunge_paper_background),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(
                    criarColorMatrix(
                        contrast = 1.12f,
                        brightness = -18f,
                        saturation = 1.05f
                    )
                )
            )

            // Tonalidade marrom quente + vinheta interna para legibilidade do texto branco
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x593A1E14),
                                Color(0x402D160E),
                                Color(0x7324100A)
                            )
                        )
                    )
            )

            // Conteúdo textual da Ficha
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 26.dp)
            ) {
                // Nome do Personagem
                Text(
                    text = "🧙‍♂️ ${ficha.nome.uppercase()}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(22.dp))

                // Raça • Classe • Alinhamento
                Text(
                    text = "${ficha.raca} • ${ficha.classe} • ${ficha.alinhamento}",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Atributos e Gancho
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "  ✦ Atributo: ${ficha.atributo}",
                        color = Color.White,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        text = "✦ Bônus Racial: ${ficha.bonusRacial}",
                        color = Color.White,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        text = "✦ Gancho: ${ficha.gancho}",
                        color = Color.White,
                        fontSize = 15.sp,
                        lineHeight = 21.sp
                    )
                }
            }
        }
    }
}

/**
 * Helper para ajustar contraste, brilho (-255f a 255f) e saturação das imagens
 */
private fun criarColorMatrix(
    contrast: Float = 1f,
    brightness: Float = 0f,
    saturation: Float = 1f
): ColorMatrix {
    val translate = (1f - contrast) / 2f * 255f + brightness
    val contrastBrightnessMatrix = ColorMatrix(
        floatArrayOf(
            contrast, 0f, 0f, 0f, translate,
            0f, contrast, 0f, 0f, translate,
            0f, 0f, contrast, 0f, translate,
            0f, 0f, 0f, 1f, 0f
        )
    )
    val saturationMatrix = ColorMatrix().apply {
        setToSaturation(saturation)
    }
    contrastBrightnessMatrix.timesAssign(saturationMatrix)
    return contrastBrightnessMatrix
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
private fun HomeScreenPreview() {
    // Preview visual idêntico ao Figma com o card visível
    Box(modifier = Modifier.fillMaxSize()) {
        HomeScreen()
    }
}
