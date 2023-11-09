package homeGen.Sudd_lavoro.Inserimento

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

class InserisciFragmentSuddivisione : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inserisci_suddivisione, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val credenziali = arguments?.get("credenziali") as loginResponse
        //recupero le credenziali
        val token = credenziali.token
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val api = Retrofit.Builder()
            .baseUrl("http://10.0.2.2/progettolaurea/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiRequests::class.java)

        var InserisciRichiesta = view?.findViewById<Button>(R.id.buttonSuddLavoro)
    // recupero i dati inseriti dall'utente
        InserisciRichiesta?.setOnClickListener{
            var commessa = view?.findViewById<TextInputEditText>(R.id.InputSuddLavoroCom)?.text.toString()
            var destinatario = view?.findViewById<TextInputEditText>(R.id.inputSuddLavoroDip)?.text.toString()
            var quantita_trasferita = view?.findViewById<TextInputEditText>(R.id.InputSuddLavoroQta)?.text.toString()
            //creo la post per la richiesta
            var myPost: InserisciSuddLavPost = InserisciSuddLavPost(destinatario,commessa,quantita_trasferita)
            val impostazioni =  "applicatio/json"
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    Log.d("mainActivityPro","va bene1")
                    //faccio la richiesta
                    val response = api.InserisciSuddivisioneRequest(impostazioni,"Bearer "+token, myPost)
                    if (response.isSuccessful) {
                        //se l'esito è positivo avviso l'utente e attivo la funzione del tasto back e torno indietro
                        Log.d("mainActivityPro","va bene2")
                        withContext(Dispatchers.Main){
                            Toast.makeText(activity,"l'inserimento è andato a buon fine", Toast.LENGTH_LONG).show()
                            activity?.onBackPressed()
                        }
                    }
                    else{
                        Log.d("mainActivityPro",response.code().toString())
                        withContext(Dispatchers.Main){
                            //se l'utente inserisce dati non corretti viene avvisato con un alert
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