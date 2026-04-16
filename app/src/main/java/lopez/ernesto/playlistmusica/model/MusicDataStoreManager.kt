package lopez.ernesto.playlistmusica.model

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.*

class MusicDataStoreManager (private val context: Context){
    private val Context.dataStore by preferencesDataStore("music_prefs")

    companion object {
        val PLAYING_LIST = stringPreferencesKey("playing_list")
    }

    val playingMusic: Flow<List<Musica>> = context.dataStore.data.map { prefs ->
        val playMusic = prefs[PLAYING_LIST] ?: ""

        playMusic.split("|").map { songData ->
            val parts = songData.split(";")
            Musica(
                nombre = parts[0],
                duracion = parts[1].toDouble(),
                Artista = parts[2],
                Album = parts[3]
            )
        }
    }

    suspend fun savePlayingList(list: List<Musica>) {
        val playMusic = list.joinToString("|") {
            "${it.nombre};${it.duracion};${it.Artista};${it.Album}"
        }
        context.dataStore.edit {
            it[PLAYING_LIST] = playMusic
        }
    }

    suspend fun logout() {
        context.dataStore.edit {
            it.clear()
        }
    }
}