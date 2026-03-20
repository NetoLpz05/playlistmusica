package lopez.ernesto.playlistmusica.viewModel

import androidx.compose.runtime.mutableStateOf
import lopez.ernesto.playlistmusica.model.Musica
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MusicViewModel : ViewModel() {
    private val avaibableMusic = listOf<Musica>(
        Musica("Ella Baila Sola", 2.45,"Eslabon Armado & Peso Pluma", "DESVELADO" ),
        Musica("La Bachata", 2.42, "Manuel Turizo", "2000"),
        Musica("Where Are Ü Now", 4.02, "Skrillex, Diplo & Justin Bieber", "Skrillex and Diplo Present Jack Ü"),
        Musica("Titanium", 4.05, "David Guetta & Sia", "Nothing but the Beat"),
        Musica("Take On Me", 3.45, "a-ha", "Hunting High and Low"),
        Musica("Counting Stars", 4.17, "OneRepublic", "Native"),
        Musica("Radioactive", 3.06, "Imagine Dragons", "Night Visions"),
        Musica("Do I Wanna Know?", 4.32, "Arctic Monkeys", "AM"),
        Musica("Sweater Weather", 4.00, "The Neighbourhood", "I Love You."),
        Musica("505", 4.13, "Arctic Monkeys", "Favourite Worst Nightmare"),
        Musica("Numb", 3.07, "Linkin Park", "Meteora"),
        Musica("In the End", 3.36, "Linkin Park", "Hybrid Theory"))



    //Lista Musica
    var randomMusic by mutableStateOf<Musica?>(null)
        private set

    //Revisar cosas
    var playingMusic by mutableStateOf(listOf<Musica>())
        private set

    //Random Fallido (buffer 50%)
    var playMusic by mutableStateOf(false)
        private set

    //Devuelve la lista completa para la pantalla 1
    fun getMusic(): List<Musica> = avaibableMusic

    fun searchMusic(index: Int){
        randomMusic = avaibableMusic[index]
    }

    fun bufferRandom(){
        val musica = randomMusic ?: return

        playMusic = true

        viewModelScope.launch {
            delay(2000L)

            val reproduciendo = (1..2).random()

            if (reproduciendo == 1) {
                playingMusic = playingMusic + musica
                playMusic = false
            } else {
                playMusic = false
                bufferRandom()
            }
        }
    }


    fun clearMusic(){
        randomMusic = null
        playMusic = false
    }

}

