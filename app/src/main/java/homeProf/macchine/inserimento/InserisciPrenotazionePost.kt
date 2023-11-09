package homeProf.macchine.inserimento
/*
Con questa classe definisco gli elementi per la richiesta di tipo POST da fare al server per inserire elementi
 */
data class InserisciPrenotazionePost(
    var codice_macchina: String,
    var inizio:String,
    var durata:String,
    var dipendente: String
)
