package lopez.ernesto.playlistmusica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import lopez.ernesto.playlistmusica.screen.MusicDetailScreen
import lopez.ernesto.playlistmusica.screen.MusicScreen
import lopez.ernesto.playlistmusica.ui.theme.PlayListMusicaTheme
import lopez.ernesto.playlistmusica.viewModel.MusicViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayListMusicaTheme {
                    MusicApp()
            }
        }
    }
}

@Composable
fun MusicApp() {
    val navController = rememberNavController()
    val viewModel: MusicViewModel = viewModel()

    NavHost(navController = navController, startDestination = "music_screen") {

        // Pantalla 1: Lista de canciones
        composable("music_screen") {
            MusicScreen(
                innerPadding = PaddingValues(),
                viewModel = viewModel,
                onSongClick = { index ->
                    navController.navigate("music_detail/$index")
                }
            )
        }

        composable(
            route = "music_detail/{songIndex}",
            arguments = listOf(navArgument("songIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("songIndex") ?: 0
            MusicDetailScreen(
                songIndex = index,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}


