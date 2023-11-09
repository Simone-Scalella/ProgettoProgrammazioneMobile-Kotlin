package homeProf.magazzino.ListaRichieste
/*
Con questa classe definisco gli elementi che vengono restituiti dal server quando risponde alla richiesta
 */
data class SelfAggiornamentoResponse(
    val id: Int,
    val dipendente: String,
    val data: String,
    val quantita: Int,
    val magazzino: String
)
