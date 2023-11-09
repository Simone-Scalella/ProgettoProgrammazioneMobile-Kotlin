package homeProf.messaggi
/*
Con questa classe definisco gli elementi che vengono restituiti dal server quando risponde alla richiesta
 */
data class MessaggiResponse(
        var id: String,
        var dipendente: String,
        var messaggio: String
)
