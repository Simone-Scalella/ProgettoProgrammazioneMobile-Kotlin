package welcome
/*
In questa classe sono definiti dei metodi per effettuare dei controlli prima di fare richieste al server
 */
object validatore {
    fun ValidazioneCredenziali(username: String,password: String): Boolean{
        if(username.equals("") || password.equals(""))
        {
            return false
        }
        else return true
    }

    fun ValidazioneCF(CF: String): Boolean{
        if(CF.length != 16){
            return true
        }
        else{
            return false
        }
    }

    fun ValidazioneCodMacchina(lunghezza: Int): Boolean{
        if(lunghezza == 0) return true
        else return false
    }
}