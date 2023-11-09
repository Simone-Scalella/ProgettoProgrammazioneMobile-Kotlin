package homeProf.magazzino.inserimento

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.esamepm.R
import com.google.android.material.textfield.TextInputEditText
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import welcome.ApiRequests
import welcome.loginResponse
import welcome.user

/*
In classe è implementata la richiesta per effettuare l'inserimento di una richiesta per usare un oggetto del magazzino
 */
class richiestaMagazzinoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_richiesta_magazzino, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    //Elementi necessari per fare un Test UI
            var user_test = user("Codice Fiscale Test","nome e cognome di test","dipendente test",
                    2.50f,2.30f,1.20f,"123456789","userTest",
                    "1990/05/20")
            var logRespTest = loginResponse("token_test",user_test)

        //recupero le  credenziali
        var credenziali: loginResponse = (arguments?.get("credenziali") ?: logRespTest) as loginResponse

        val token = credenziali.token
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val api = Retrofit.Builder()
            .baseUrl("http://10.0.2.2/progettolaurea/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiRequests::class.java)

        var RichiestaMagazzino = view?.findViewById<Button>(R.id.buttonRichiestaMagazzino)
//Quando viene premuto il bottone di invio  vengono recuperate le informazioni inserite dall'utente
        RichiestaMagazzino?.setOnClickListener{
            var dipendente = credenziali.user.CF
            var articolo = view?.findViewById<TextInputEditText>(R.id.inputArticolo)?.text.toString()
            var quantita = view?.findViewById<TextInputEditText>(R.id.InputQuantita)?.text.toString()
            var anno = view?.findViewById<TextInputEditText>(R.id.anno)?.text.toString()
            var mese = view?.findViewById<TextInputEditText>(R.id.mese)?.text.toString()
            var giorno = view?.findViewById<TextInputEditText>(R.id.giorno)?.text.toString()
            var data = anno +"/"+ mese +"/"+giorno
            // viene creata la post per la richiesta
            var myPost: richiestaMagazzinoPost = richiestaMagazzinoPost(dipendente,data,quantita,articolo)
            val impostazioni =  "applicatio/json"
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    Log.d("mainActivityPro","va bene1")
                    val response = api.richiestaMagazzinoRequest(impostazioni,"Bearer "+token, myPost)
                    if (response.isSuccessful) {
                        Log.d("mainActivityPro","va bene2")
                        withContext(Dispatchers.Main){
                            //se la risposta è positiva viene stampato un messaggio e si torna indietro
                            Toast.makeText(activity,"l'inserimento è andato a buon fine",Toast.LENGTH_LONG).show()
                            activity?.onBackPressed()
                        }
                    }
                    else{
                        //se la risposta è negativa l'utente ha inserito dei dati che noon sono corretti, e viene avvisato con un AlertDialog
                        withContext(Dispatchers.Main){
                            AlertDialog.Builder(context)
                                    .setTitle("Si è verificato un problema")
                                    .setMessage("Hai inserito dei dati non corretti, riprova")
                                    .setPositiveButton("Ok", DialogInterface.OnClickListener(){ dialog, which -> dialog.dismiss()  })
                                    .create().show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("MainActivity", e.toString())
                    }
                }
            }
        }
    }

}