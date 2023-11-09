package homeProf.RichiesteLavoro.Inserimento

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

class InserimentoTrasferimentoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inserimento_trasferimento, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val credenziali = arguments?.get("credenziali") as loginResponse
    // vengono recuperate le credenziali
        val token = credenziali.token
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val api = Retrofit.Builder()
            .baseUrl("http://10.0.2.2/progettolaurea/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiRequests::class.java)

        var InserisciRichiesta = view?.findViewById<Button>(R.id.buttonRichiestaTrasferimento)
 //vengono recuperati i dati inseriti dall'utente
        InserisciRichiesta?.setOnClickListener{
            var dipendente = credenziali.user.CF
            var commessa = view?.findViewById<TextInputEditText>(R.id.InputCommessa)?.text.toString()
            var destinatario = view?.findViewById<TextInputEditText>(R.id.inputDipendente)?.text.toString()
            var anno = view?.findViewById<TextInputEditText>(R.id.annoRichiesta)?.text.toString()
            var mese = view?.findViewById<TextInputEditText>(R.id.meseRichiesta)?.text.toString()
            var giorno = view?.findViewById<TextInputEditText>(R.id.giornoRichiesta)?.text.toString()
            var data = anno +"/"+mese +"/"+giorno+" "
            var quantita_trasferita = view?.findViewById<TextInputEditText>(R.id.inputQuantitaTrasferita)?.text.toString()
            var valore = "-"+view?.findViewById<TextInputEditText>(R.id.inputValore)?.text.toString()
            //viene creata la post per la richiesta
            var myPost: InserisciTrasferimentoPost = InserisciTrasferimentoPost(dipendente,commessa,data,valore,quantita_trasferita,destinatario)
            val impostazioni =  "applicatio/json"
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    Log.d("mainActivityPro","va bene1")
                    val response = api.InserisciTrasferimentoRequest(impostazioni,"Bearer "+token, myPost)
                    if (response.isSuccessful) {
                        Log.d("mainActivityPro","va bene2")
                        withContext(Dispatchers.Main){
                            // se la risposta è positiva viene visualizzato un messaggio e torna indietro
                            Toast.makeText(activity,"l'inserimento è andato a buon fine", Toast.LENGTH_LONG).show()
                            activity?.onBackPressed()
                        }
                    }
                    else{
                        //se la risposta è negativa viene avvisato l'utente con un Alert che segnala l'inserimento di dati non corretti
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