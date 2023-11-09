package homeProf.macchine.inserimento

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

/*
In questa classe viene implementata la richiesta per l'inserimento di una prenotazione per usare una macchina pubblica
 */
class InserisciPrenotazioneFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inserisci_prenotazione, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val credenziali = arguments?.get("credenziali") as loginResponse

        val token = credenziali.token
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val api = Retrofit.Builder()
            .baseUrl("http://10.0.2.2/progettolaurea/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiRequests::class.java)

        var InserisciRichiesta = view?.findViewById<Button>(R.id.buttonPrenotazione)
    //Qui vengono presi i vari dati inseriti dall'utente
        InserisciRichiesta?.setOnClickListener{
            var dipendente = credenziali.user.CF
            var codice_macchina = view?.findViewById<TextInputEditText>(R.id.inputCodiceMaccPrenotazione)?.text.toString()
            var durata = view?.findViewById<TextInputEditText>(R.id.InputDurata)?.text.toString()
            var anno = view?.findViewById<TextInputEditText>(R.id.annoPrenotazione)?.text.toString()
            var mese = view?.findViewById<TextInputEditText>(R.id.mesePrenotazione)?.text.toString()
            var giorno = view?.findViewById<TextInputEditText>(R.id.giornoPrenotazione)?.text.toString()
            var ora = view?.findViewById<TextInputEditText>(R.id.oraPrenotazione)?.text.toString()
            var data = anno +"/"+mese +"/"+giorno+" "+ora
            //viene creata la POST per la richiesta
            var myPost: InserisciPrenotazionePost = InserisciPrenotazionePost(codice_macchina,data,durata,dipendente)
            val impostazioni =  "applicatio/json"
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    Log.d("mainActivityPro","va bene1")
                    val response = api.InserisciCodaRequest(impostazioni,"Bearer "+token, myPost)
                    //se la risposta del server è positiva viene visualizzato un Toast e successivamente si attiva la funzione di tornare indietro del tasto Back
                    if (response.isSuccessful) {
                        Log.d("mainActivityPro","va bene2")
                        withContext(Dispatchers.Main){
                            Toast.makeText(activity,"l'inserimento è andato a buon fine", Toast.LENGTH_LONG).show()
                            activity?.onBackPressed()
                        }
                    }
                    else{
                        //nel caso in cui l'utente inserisce dati non corretti, la risposta del server sarà negativa e verra avvisato l'utente con un AlertDialog
                        Log.d("mainActivityPro",response.code().toString())
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