package homeProf.macchine.liste

/*
Con questa classe definisco gli elementi che saranno restituiti dal server dopo che avrà accettato la richiesta
 */
data class PrenotazioniGenericheResponse(
    var codice_macchina: String,
    var tipo_macchina: String,
    var coda: MutableList<SelfPrenotazioniResponse>
)
