package homeProf.magazzino

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esamepm.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import homeProf.magazzino.lista.CustomAdapterListaMagazzino
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import welcome.ApiRequests
import welcome.loginResponse
import java.util.*

class listaMagazzino : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.lista_magazzino_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    //vengono recuperate le credenziali
        var credenziali = arguments?.get("credenziali") as loginResponse

        var token = credenziali.token

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val api = Retrofit.Builder()
            .baseUrl("http://10.0.2.2/progettolaurea/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiRequests::class.java)

        risultato(api,token)

    }
//viene fatta la richiesta al server
    fun risultato(api: ApiRequests,token: String){
        val impostazioni =  "applicatio/json"
        Log.d("mainActivityPro","va bene1")
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("mainActivityPro","va bene2")
            try {
                Log.d("mainActivityPro","va bene3")
                var response = api.ListaMagazzinoRequest(impostazioni,"Bearer "+token)
            // se la risposta è positiva viene inizializzata la RecycleView con il risultato
                if (response.isSuccessful) {
                    Log.d("mainActivityPro","va bene3")
                    var data = response.body()!!
                    withContext(Dispatchers.Main){
                        //viene inizializzata la recycleView con la risposta del server
                        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view_simo)
                        recyclerView?.layoutManager = LinearLayoutManager(activity)
                        recyclerView?.adapter= CustomAdapterListaMagazzino(data)
                        recyclerView?.addItemDecoration(
                                DividerItemDecoration(
                                        activity,
                                        DividerItemDecoration.VERTICAL
                                )
                        )
                    }
                }
                else {
                    Log.d("mainActivityPro",response.code().toString())
                }

           } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("MainActivity", e.toString())
                }
            }
        }
    }
}