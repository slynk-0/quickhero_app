package com.deamon.quickheroapp.main_ui.screens

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deamon.quickheroapp.backend.FichaRPG
import com.deamon.quickheroapp.R
import coil.compose.SubcomposeAsyncImage
import kotlin.random.Random

@Composable
fun DetailsScreen(
    ficha: FichaRPG,
    onMenuClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onLogoClick: () -> Unit = {},
    onGenerateImageClick: (FichaRPG) -> Unit = {}
) {
    var imageUrl by remember(ficha.id) { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopBarDetails(
                onMenuClick = onMenuClick,
                onFavoritesClick = onFavoritesClick,
                onLogoClick = onLogoClick
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 1. WALLPAPER DE FUNDO (Mesmo posicionamento e filtros da HomeScreen)
            Image(
                painter = painterResource(id = R.drawable.baixados__2_),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(
                    criarColorMatrixDetalhes(
                        contrast = 1.08f,
                        brightness = -12f,
                        saturation = 1.15f
                    )
                )
            )

            // Camada atmosférica para contraste do nome e da ficha expandida
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x4D2A0808),
                                Color(0x331A0B0B),
                                Color(0x730D0606)
                            )
                        )
                    )
            )

            // 2. CONTEÚDO CENTRALIZADO
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(14.dp))

                // Nome do Personagem em destaque acima da ficha
                Text(
                    text = ficha.nome.replaceFirstChar { it.uppercase() },
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color(0x991A0505),
                            offset = Offset(0f, 3f),
                            blurRadius = 8f
                        )
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                // 3. FICHA EXPANDIDA (Ocupa o restante da altura até a margem inferior)
                CardFichaExpandida(
                    ficha = ficha,
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 26.dp),
                    onGenerateImageClick = {
                        imageUrl = construirUrlImagemIA(ficha)
                        onGenerateImageClick(ficha)
                    }
                )
            }
        }
    }
}

@Composable
private fun TopBarDetails(
    onMenuClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onLogoClick: () -> Unit
) {
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

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onLogoClick)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "D&D Quick\nHero",
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 26.sp,
                textAlign = TextAlign.Center
            )
        }

        IconButton(onClick = onFavoritesClick) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favoritar ou Ver Salvos",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun CardFichaExpandida(
    ficha: FichaRPG,
    imageUrl: String?,
    modifier: Modifier = Modifier,
    onGenerateImageClick: () -> Unit
) {
    val shapeCard = RoundedCornerShape(24.dp)
    val corMolduraMarrom = Color(0xFF3E231D)

    Surface(
        modifier = modifier
            .shadow(elevation = 16.dp, shape = shapeCard)
            .clip(shapeCard),
        shape = shapeCard,
        border = BorderStroke(width = 9.dp, color = corMolduraMarrom),
        color = Color(0xFF5A392B)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Textura de pergaminho envelhecido (grunge_paper_background)
            Image(
                painter = painterResource(id = R.drawable.grunge_paper_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(
                    criarColorMatrixDetalhes(
                        contrast = 1.12f,
                        brightness = -18f,
                        saturation = 1.05f
                    )
                )
            )

            // Tonalidade marrom quente + vinheta interna
            Box(
                modifier = Modifier
                    .fillMaxSize()
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

            // Conteúdo Interno: Dados no topo + Botão "Gere uma imagem" na base
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 22.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Bloco Superior: Atributos e Gancho (Mesmo alinhamento da Home)
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "🧙‍♂️ ${ficha.nome.uppercase()}",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    Text(
                        text = "${ficha.raca} • ${ficha.classe} • ${ficha.alinhamento}",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.height(24.dp))

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

                AnimatedVisibility(
                    visible = imageUrl != null,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    imageUrl?.let { url ->
                        MolduraRetratoIA(url = url, nomePersonagem = ficha.nome)
                    }
                }

                // Bloco Inferior: Botão Pill "Gere uma imagem" centralizado
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BotaoGerarImagem(
                        jaGerou = imageUrl != null,
                        onClick = onGenerateImageClick
                    )
                }
            }
        }
    }
}

@Composable
private fun MolduraRetratoIA(url: String, nomePersonagem: String) {
    val shapeRetrato = RoundedCornerShape(16.dp)

    Surface(
        modifier = Modifier
            .size(210.dp)
            .shadow(elevation = 10.dp, shape = shapeRetrato),
        shape = shapeRetrato,
        border = BorderStroke(2.dp, Color(0xFFB58943)),
        color = Color(0xFF280D0B)
    ) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = "Retrato de $nomePersonagem",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFB58943),
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Invocando retrato...",
                        color = Color(0xFFEAD7BE),
                        fontSize = 12.sp
                    )
                }
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Falha ao invocar imagem.\nVerifique sua conexão.",
                        color = Color(0xFFEAD7BE),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    }
}

private fun construirUrlImagemIA(ficha: FichaRPG): String {
    val racaEn = when (ficha.raca.lowercase()) {
        "elfo" -> "elf"
        "anão" -> "dwarf"
        "humano" -> "human"
        "tiefling" -> "tiefling with horns"
        else -> "fantasy hero"
    }

    val classeEn = when (ficha.classe.lowercase()) {
        "guerreiro" -> "warrior in armor"
        "mago" -> "wizard casting arcane magic"
        "ladino" -> "rogue assassin in hood"
        "clérigo" -> "cleric paladin priest"
        "bárbaro" -> "barbarian berserker"
        "bardo" -> "bard musician"
        "paladino" -> "holy paladin knight"
        "patrulheiro" -> "ranger archer"
        else -> "adventurer"
    }

    val seedAleatoria = Random.nextInt(1, 999999)
    val prompt = "D&D character portrait, $racaEn $classeEn, ${ficha.alinhamento}, " +
            "detailed fantasy digital painting, dramatic lighting, artstation style"

    val promptCodificado = Uri.encode(prompt)
    return "https://image.pollinations.ai/prompt/$promptCodificado?width=512&height=512&seed=$seedAleatoria&nologo=true"
}

@Composable
private fun BotaoGerarImagem(
    jaGerou: Boolean = false,
    onClick: () -> Unit
) {
    val shapeBotao = RoundedCornerShape(50) // Formato pílula (cápsula)
    val corFundoBotao = Color(0xFF280D0B)  // Marrom-vinho escuro do protótipo
    val corBordaDourada = Color(0xFFB58943) // Filete dourado-âmbar
    val corTextoCreme = Color(0xFFEAD7BE)   // Dourado claro / pergaminho

    val restoTexto = if (jaGerou) "ERAR NOVAMENTE" else "ERE UMA IMAGEM"

    val textoVersalete = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
        ) {
            append("G")
        }
        withStyle(
            style = SpanStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
        ) {
            append(restoTexto)
        }
    }

    Surface(
        modifier = Modifier
            .width(246.dp)
            .height(54.dp)
            .shadow(elevation = 8.dp, shape = shapeBotao)
            .clip(shapeBotao)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = corBordaDourada),
                onClick = onClick
            ),
        shape = shapeBotao,
        color = corFundoBotao,
        border = BorderStroke(width = 1.5.dp, color = corBordaDourada)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = textoVersalete,
                color = corTextoCreme,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color(0xAA000000),
                        offset = Offset(0f, 2f),
                        blurRadius = 4f
                    )
                )
            )
        }
    }
}

private fun criarColorMatrixDetalhes(
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
private fun DetailsScreenPreview() {
    val fichaExemplo = FichaRPG(
        id = 1,
        nome = "Faerion",
        raca = "Elfo",
        classe = "Mago",
        alinhamento = "Caótico e Bom",
        atributo = "Mente Brilhante",
        bonusRacial = "+2 Agilidade",
        gancho = "Guarda o mapa de um tesouro indecifrável."
    )

    DetailsScreen(ficha = fichaExemplo)
}