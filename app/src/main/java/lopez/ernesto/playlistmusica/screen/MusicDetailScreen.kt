package lopez.ernesto.playlistmusica.screen

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import lopez.ernesto.playlistmusica.viewModel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicDetailScreen(songIndex: Int, viewModel: MusicViewModel = viewModel(), onBack: () -> Unit = {}){
    val musica = viewModel.getMusic()[songIndex]

    Scaffold(
        topBar = { TopAppBar(title = { Text("Detalle de la canción") }, navigationIcon = {
                    IconButton(onClick = {
                        viewModel.clearMusic()
                        onBack() }) { Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) {
        padding ->
        Column (modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Text("Music", style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(16.dp))

            Text(musica.nombre, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            Text("Artista: ${musica.Artista}", style = MaterialTheme.typography.bodyLarge)
            Text("Album: ${musica.Album}", style = MaterialTheme.typography.bodyLarge)
            Text("Duracion: ${musica.duracion} min", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(32.dp))

            when{
                viewModel.playMusic ->{
                    CircularProgressIndicator()
                    Spacer(Modifier.height(12.dp))
                    Text(text = "Cargando...", style = MaterialTheme.typography.titleMedium)
                }
                viewModel.playingMusic.contains(musica) -> {
                    Text("Reproducir cancion", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(onClick = {viewModel.clearMusic()}) {
                        Text("Detener")
                    }
                }
                else -> {
                    Button(onClick = {viewModel.searchMusic(songIndex)
                    viewModel.bufferRandom()}, modifier = Modifier.fillMaxWidth()) {
                        Text("Reproducir cancion")
                    }
                }
            }
        }
    }
}