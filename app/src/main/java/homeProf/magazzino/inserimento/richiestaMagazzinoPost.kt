package homeProf.magazzino.inserimento
/*
Con questa classe definisco gli elementi per la richiesta di tipo POST da fare al server
 */
data class richiestaMagazzinoPost(
        var dipendente: String,
        var data: String,
        var quantita: String,
        var magazzino: String
)
