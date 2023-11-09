package welcome

import java.io.Serializable
/*
Con questa classe definisco una parte della risposta che mi viene restituita dall'applicazione web,
contiene i dati dell'utente che effettua i login
 */
data class user(
    val CF:String,
    val nome_cognome:String,
    val tipo_dipendente:String,
    val importo_orario_feriale:Float,
    val importo_orario_regolare:Float,
    val importo_orario_straordinario:Float,
    val IBAN:String,
    val username:String,
    val data_di_nascita:String,
):Serializable
