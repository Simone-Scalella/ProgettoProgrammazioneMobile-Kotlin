package welcome

import java.io.Serializable

/*
Con questa classe vado a definire la risposta che sarà restituita dall'applicazione web, mi serve specificare
'Serializable' perchè dovrò inserirla in un Bundle
 */

data class loginResponse(
   val token:String,
   val user:user,
) : Serializable

