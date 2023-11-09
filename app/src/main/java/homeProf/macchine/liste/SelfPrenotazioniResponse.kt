package homeProf.macchine.liste
/*
Con questa classe definisco gli elementi che saranno restituiti dal server sotto forma di json quando accetta la richiesta
 */
data class SelfPrenotazioniResponse(
    val id: Int,
    val dipendente: String,
    val codice_macchina: String,
    val inizio: String,
    val durata: String
)
