package welcome
/*
Con questa classe vado a definire gli elementi della richiesta POST che andrò a fare all'applicazione web per il login
 */
data class logPost(
        val username: String,
        val password: String
)
