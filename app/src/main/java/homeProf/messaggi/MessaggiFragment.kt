package homeProf.messaggi

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
import homeProf.RichiesteLavoro.CustomAdapterSelfRichieste
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import welcome.ApiRequests
import welcome.loginResponse

class MessaggiFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messaggi, container, false)
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

        risultato(api,token)

    }
    //faccio la richiesta al server
    fun risultato(api: ApiRequests, token: String){
        val impostazioni =  "applicatio/json"
        Log.d("mainActivityPro","va bene1")
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("mainActivityPro","va bene2")
            try {
                Log.d("mainActivityPro","va bene3")
                val response = api.MessaggiRequest(impostazioni,"Bearer "+token)
            // se la risposta è positiva viene inizializzata la recycleView
                if (response.isSuccessful) {
                    Log.d("mainActivityPro","va bene3")
                    var data = response.body()!!
                    withContext(Dispatchers.Main){
                        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view_simo_messaggi)
                        recyclerView?.layoutManager = LinearLayoutManager(activity)
                        recyclerView?.adapter= CustomAdapterMessaggi(data)
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