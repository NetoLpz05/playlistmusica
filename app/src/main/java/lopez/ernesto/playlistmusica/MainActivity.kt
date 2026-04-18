package lopez.ernesto.playlistmusica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import lopez.ernesto.playlistmusica.DataStore.DataStoreManager
import lopez.ernesto.playlistmusica.screen.LoginScreen
import lopez.ernesto.playlistmusica.screen.MusicDetailScreen
import lopez.ernesto.playlistmusica.screen.MusicScreen
import lopez.ernesto.playlistmusica.ui.theme.PlayListMusicaTheme
import lopez.ernesto.playlistmusica.viewModel.MusicViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStore = DataStoreManager(this)

        enableEdgeToEdge()
        setContent {
            PlayListMusicaTheme {
                val isLoggedIn by dataStore.isLoggedInFlow.collectAsState(initial = false)
                val username by dataStore.usernameFlow.collectAsState(initial = "")
                val scope = rememberCoroutineScope()

                if (isLoggedIn) {
                    MusicApp(
                        username = username,
                        onLogout = {
                            scope.launch { dataStore.logout() }
                        }
                    )
                } else {
                    LoginScreen(
                        onLoginSuccess = { user ->
                            scope.launch { dataStore.saveSession(user) }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MusicApp(username: String, onLogout: () -> Unit) {
    val navController = rememberNavController()
    val viewModel: MusicViewModel = viewModel()

    NavHost(navController = navController, startDestination = "music_screen") {

        composable("music_screen") {
            MusicScreen(
                innerPadding = PaddingValues(),
                viewModel = viewModel,
                username = username,
                onLogout = onLogout,
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

