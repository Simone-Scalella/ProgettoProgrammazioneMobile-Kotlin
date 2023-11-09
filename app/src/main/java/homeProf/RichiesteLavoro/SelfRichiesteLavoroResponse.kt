package homeProf.RichiesteLavoro
/*
Con questa classe definisco gli elementi che vengono restituiti dal server quando risponde alla richiesta
 */
data class SelfRichiesteLavoroResponse(
        var id: String,
        var codice_trasf: Int,
        var commessa: String,
        var dipendente: Dipendente,
        var data_trasferimento: String,
        var valore_trasferito: String,
        var quantita_trasferita: String,
        var confermato: String
)
