package homeProf.RichiesteLavoro.delete
/*
Con questa classe definisco gli elementi che compongono la richiesta di tipo POST per cancellare un trasferimento di lavoro
 */
data class DeleteTrasferimentoPost(
    var codice_trasf: String
)
