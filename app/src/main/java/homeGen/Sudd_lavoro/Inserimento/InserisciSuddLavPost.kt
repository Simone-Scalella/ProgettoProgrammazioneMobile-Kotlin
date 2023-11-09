package homeGen.Sudd_lavoro.Inserimento
/*
in questa classe sono definiti gli elementi che compongono la richiesta Post da fare al server per l'inserimento
 */
data class InserisciSuddLavPost(
        var dipendente: String,
        var commessa: String,
        var quantita_assegnata: String
)
