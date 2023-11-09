package homeGen.Sudd_lavoro.ListaCF
/*
In questa classe vado a definire gli elementi che andranno a comporre la richiesta di tipo Post da fare al server per cancellare una suddivisione lavoro
 */
data class DeleteSuddPost(
    var commessa: String,
    var dipendente: String
)
