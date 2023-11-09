package welcome

import homeGen.Sudd_lavoro.Inserimento.InserisciSuddLavPost
import homeGen.Sudd_lavoro.ListaCF.DeleteSuddPost
import homeProf.RichiesteLavoro.Inserimento.InserisciTrasferimentoPost
import homeProf.messaggi.MessaggiResponse
import homeProf.RichiesteLavoro.SelfRichiesteLavoroResponse
import homeProf.RichiesteLavoro.delete.DeleteTrasferimentoPost
import homeProf.macchine.liste.DeletePrenotazionePost
import homeProf.macchine.inserimento.InserisciPrenotazionePost
import homeProf.macchine.liste.PrenotazioniGenericheResponse
import homeProf.macchine.liste.SelfPrenotazioniResponse
import homeProf.magazzino.ListaRichieste.SelfAggiornamentoResponse
import homeProf.magazzino.inserimento.RichiestaMagazzinoResponse
import homeProf.magazzino.inserimento.richiestaMagazzinoPost
import homeProf.magazzino.lista.Articoli
import homeProf.suddivisione_lavoro.SelfSuddLavoroResponse
import retrofit2.Response
import retrofit2.http.*

/*
In questa parte dell'applicazione sono state definite tutte le chiamate ai servizi messi a disposizione dall'applicazione web
 */

interface ApiRequests {

    @POST("public/api/login")
    suspend fun Login(@Body post: logPost): Response<loginResponse>

    @GET("public/api/authed/magazzino/")
    suspend fun ListaMagazzinoRequest(@Header("Accept") setting: String,@Header("Authorization") token: String ): Response<MutableList<Articoli>>

    @POST("public/api/authed/aggiornamento/insert")
    suspend fun richiestaMagazzinoRequest(@Header("Accept") setting: String,@Header("Authorization") token: String,@Body post: richiestaMagazzinoPost): Response<RichiestaMagazzinoResponse>

    @GET("public/api/authed/aggiornamento/self")
    suspend fun SelfAggiornamentoRequest(@Header("Accept") setting: String,@Header("Authorization") token: String ): Response<MutableList<SelfAggiornamentoResponse>>

    @GET("public/api/authed/gen/pro/code/self")
    suspend fun SelfCodeRequest(@Header("Accept") setting: String,@Header("Authorization") token: String ): Response<MutableList<SelfPrenotazioniResponse>>

    @GET("public/api/authed/gen/pro/suddivisione_lavoro/self")
    suspend fun SelfSuddLavRequest(@Header("Accept") setting: String,@Header("Authorization") token: String ): Response<MutableList<SelfSuddLavoroResponse>>

    @GET("public/api/authed/gen/pro/macchine_pubbliche/key/")
    suspend fun PrenotazioniGenRequest(@Header("Accept") setting: String,@Header("Authorization") token: String, @Query("codice_macchina",encoded = true) codice_macchina: String): Response<PrenotazioniGenericheResponse>

    @GET("public/api/authed/gen/pro/trasferimento_lavoro/self")
    suspend fun SelfRichiesteRequest(@Header("Accept") setting: String,@Header("Authorization") token: String): Response<MutableList<SelfRichiesteLavoroResponse>>

    @GET("public/api/authed/gen/pro/messaggi/self/NoEliminazione")
    suspend fun MessaggiNOEliminazioneRequest(@Header("Accept") setting: String,@Header("Authorization") token: String ): Response<MutableList<MessaggiResponse>>

    @GET("public/api/authed/gen/pro/messaggi/self")
    suspend fun MessaggiRequest(@Header("Accept") setting: String,@Header("Authorization") token: String ): Response<MutableList<MessaggiResponse>>

    @POST("public/api/authed/gen/pro/code/insert")
    suspend fun InserisciCodaRequest(@Header("Accept") setting: String,@Header("Authorization") token: String,@Body post: InserisciPrenotazionePost): Response<RichiestaMagazzinoResponse>

    @POST("public/api/authed/gen/pro/code/delete")
    suspend fun EliminaCodaRequest(@Header("Accept") setting: String,@Header("Authorization") token: String,@Body post: DeletePrenotazionePost): Response<RichiestaMagazzinoResponse>

    @POST("public/api/authed/gen/pro/trasferimento_lavoro/insert")
    suspend fun InserisciTrasferimentoRequest(@Header("Accept") setting: String,@Header("Authorization") token: String,@Body post: InserisciTrasferimentoPost): Response<RichiestaMagazzinoResponse>

    @POST("public/api/authed/gen/pro/trasferimento_lavoro/delete")
    suspend fun EliminaTrasferimentoRequest(@Header("Accept") setting: String,@Header("Authorization") token: String,@Body post: DeleteTrasferimentoPost): Response<RichiestaMagazzinoResponse>

    @POST("public/api/authed/gen/suddivisione_lavoro/insert")
    suspend fun InserisciSuddivisioneRequest(@Header("Accept") setting: String,@Header("Authorization") token: String,@Body post: InserisciSuddLavPost): Response<RichiestaMagazzinoResponse>

    @GET("public/api/authed/gen/suddivisione_lavoro/key/{CF}")
    suspend fun ListaSuddLavCFRequest(@Header("Accept") setting: String,@Header("Authorization") token: String, @Path("CF") codiceFiscale: String ): Response<MutableList<SelfSuddLavoroResponse>>

    @POST("public/api/authed/gen/suddivisione_lavoro/delete")
    suspend fun EliminaSuddivisioneRequest(@Header("Accept") setting: String,@Header("Authorization") token: String,@Body post: DeleteSuddPost): Response<RichiestaMagazzinoResponse>
}