package homeProf.suddivisione_lavoro
/*
Con questa classe definisco gli elementi che vengono restituiti dal server quando risponde alla richiesta
 */
data class SelfSuddLavoroResponse(
        val commessa: commessa?,
        val dipendente: String,
        val valore_lavoro: String,
        val quantita_assegnata: String,
        var data_conclusione:String?
)
