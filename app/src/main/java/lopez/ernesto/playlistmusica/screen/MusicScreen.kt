package lopez.ernesto.playlistmusica.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import lopez.ernesto.playlistmusica.viewModel.MusicViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight

@Composable
fun MusicScreen(innerPadding: PaddingValues, viewModel: MusicViewModel = viewModel(), onSongClick: (Int) -> Unit = {}){
    Column (modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)){
        Text(text = "Playlist de musica", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
        LazyColumn (verticalArrangement = Arrangement.spacedBy(8.dp)){
            items(viewModel.getMusic().indices.toList()) { index ->
                val musica = viewModel.getMusic()[index]
                Card (modifier = Modifier.fillMaxSize().clickable{ onSongClick(index)},
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)){
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = musica.nombre, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                        Text(text = musica.Artista, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = musica.Album, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MusicScreenPreview(){
    MusicScreen(PaddingValues(18.dp))
}