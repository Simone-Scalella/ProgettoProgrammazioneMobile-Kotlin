package homeProf.RichiesteLavoro.Inserimento
/*
Con questa classe definisco gli elementi che compongono la richiesta POST fatta al server
 */
data class InserisciTrasferimentoPost(
    var dipendente: String,
    var commessa: String,
    var data_trasferimento: String,
    var valore_trasferito: String,
    var quantita_trasferita: String,
    var destinatario: String
)
