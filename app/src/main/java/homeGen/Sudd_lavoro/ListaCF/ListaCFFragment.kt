package homeGen.Sudd_lavoro.ListaCF

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

class ListaCFFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_c_f, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //recupero le credenziali
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

        var bottone_invio = view?.findViewById<Button>(R.id.button_cf_dipendente)
        var cf = view?.findViewById<TextInputEditText>(R.id.input_CF_dipendente)
        bottone_invio?.setOnClickListener() {
            //prima di fare la richiesta al server bisogna controllare che l'utente abbia inserito un codice fiscale corretto
            if(validatore.ValidazioneCF(cf?.text.toString())) {
                Toast.makeText(context,"devi inserire un codice fiscale corretto ", Toast.LENGTH_LONG).show()
            }
            else
                risultato(api, token,cf?.text.toString())
        }
    }
    //viene fatta la richiesta al server
    fun risultato(api: ApiRequests, token: String, CF: String){
        val impostazioni =  "applicatio/json"
        Log.d("mainActivityPro","va bene1")
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("mainActivityPro","va bene2")
            try {
                Log.d("mainActivityPro","va bene3")
                val response = api.ListaSuddLavCFRequest(impostazioni,"Bearer "+token,CF)
            // se la risposta è positiva viene inizializzata la recycleView con la risposta del server
                if (response.isSuccessful) {
                    Log.d("mainActivityPro","va bene3")
                    var data = response.body()!!
                    withContext(Dispatchers.Main){
                        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view_simo_SuddivisioniCF)
                        recyclerView?.layoutManager = LinearLayoutManager(activity)
                        recyclerView?.adapter= CustomAdapterListaCF(data,token)
                        recyclerView?.addItemDecoration(
                            DividerItemDecoration(
                                activity,
                                DividerItemDecoration.VERTICAL
                            )
                        )
                    }
                }
                else {
                    //se il codice fiscale ha generato un errore lato server l'utente viene avvisato con un alert
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