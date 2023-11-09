package homeProf.macchine.liste

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import welcome.validatore
/*
In questa RecycleView vado a inserire un campo che l'utente deve compilare con il codice di un macchinario per visualizzare tutte le prenotazione fatte per usarlo
*/
class PrenotazioniGenericheFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prenotazioni_generiche, container, false)
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

        var bottone_invio = view?.findViewById<Button>(R.id.button_codice_macchina)
        var codice_macchina = view?.findViewById<TextInputEditText>(R.id.input_cod_macchina)
        //Quando il bottone di invio viene premuto bisogna effettuare un controllo prima di far partire la richiesta
        bottone_invio?.setOnClickListener() {
            if(validatore.ValidazioneCodMacchina(codice_macchina?.text.toString().length)) {
                    Toast.makeText(context,"devi inserire il codice di una macchina ",Toast.LENGTH_LONG).show()
                }
            else
            risultato(api, token,codice_macchina?.text.toString())
        }
    }
//Qui viene fatta la chiamata al server il cui risultato viene visualizzato nella RecycleView
    fun risultato(api: ApiRequests, token: String,cod_macchina: String){
        val impostazioni =  "applicatio/json"
        Log.d("mainActivityPro","va bene1")
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("mainActivityPro","va bene2")
            try {
                Log.d("mainActivityPro","va bene3")
                val response = api.PrenotazioniGenRequest(impostazioni,"Bearer "+token,cod_macchina)

                if (response.isSuccessful) {
                    Log.d("mainActivityPro","va bene3")
                    var data = response.body()!!
                    withContext(Dispatchers.Main){
                        //se la risposta è positiva viene inizializzata la recycleView con la risposta del server
                        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view_simo_PrenotazioniGeneriche)
                        recyclerView?.layoutManager = LinearLayoutManager(activity)
                        recyclerView?.adapter= CustomAdapterSelfPrenotazioni(data.coda,token)
                        recyclerView?.addItemDecoration(
                                DividerItemDecoration(
                                        activity,
                                        DividerItemDecoration.VERTICAL
                                )
                        )
                    }
                }
                else {
                    //Se l'utente inserisce un codice errato viene avvisato con un AlertDialog
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