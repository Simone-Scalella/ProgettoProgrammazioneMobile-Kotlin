package homeProf.macchine.liste

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.esamepm.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import welcome.ApiRequests
/*
Con questa classe vado a definire la RecycleView che contiene gli elementi restituiti dal server, inoltre permette di eliminare degli elemtni
 */
class CustomAdapterSelfPrenotazioni(private val data: List<SelfPrenotazioniResponse>, val token: String) : RecyclerView.Adapter<CustomAdapterSelfPrenotazioni.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view2, parent, false)
        return ViewHolder(rowItem)
    }
    //vado a valorizzare tutti gli elementi della lista,devo creare una variabile che contiene il token, per permettere alll'utente di cancellare un elemento
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView1.text = data[position].codice_macchina
        holder.textView2.text = data[position].inizio
        holder.textView3.text = "durata: "+data[position].durata
        holder.textView0.text = data[position].id.toString()
        holder._token = token
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnLongClickListener{
        val textView0: TextView
        val textView1: TextView
        val textView2: TextView
        val textView3: TextView
        lateinit var _token: String

        //questa è la funzione che si attiva al verificarsi dell'evento onLongClick, viene visualizzato un AlertDialog
        override fun onLongClick(view: View?): Boolean {
            AlertDialog.Builder(view?.context)
                .setTitle("Eliminare questo elemento ?")
                .setMessage("Se premi ok l'elemento selezionato verrà eliminato")
                .setPositiveButton("Ok",
                    DialogInterface.OnClickListener(){ dialog, which -> EliminaElemento(view!!,textView0,_token) })
                .setNegativeButton( "Annulla" , DialogInterface.OnClickListener(){ dialog, which -> dialog.dismiss() })
                .create().show()
            return true
        }
        //se l'utente clissa su ok parte la richiesta al server di eliminazione di quell'elemento
        fun EliminaElemento(view: View, text: TextView,tokenElim: String){
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val api = Retrofit.Builder()
                .baseUrl("http://10.0.2.2/progettolaurea/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ApiRequests::class.java)
            //un elemento invisibile della lista viene controllato per evitare che un utente elimini 2 volte lo stesso elemento
            if(text.text.toString().equals("")){
                Toast.makeText(view.context, "non puoi eliminare 2 volte lo stesso elemento", Toast.LENGTH_SHORT).show()
            }
            else {
                var id = text.text.toString()
                Log.d("msn",id)
                Log.d("msn",tokenElim)
                val myPost: DeletePrenotazionePost = DeletePrenotazionePost(id)
                val impostazioni = "applicatio/json"
                Log.d("mainActivityPro", "va bene1")
                GlobalScope.launch(Dispatchers.IO) {
                    Log.d("mainActivityPro", "va bene2")
                    try {
                        Log.d("mainActivityPro", "va bene3")
                        val response = api.EliminaCodaRequest(impostazioni, "Bearer " + tokenElim, myPost)

                        if (response.isSuccessful) {
                            Log.d("mainActivityPro", "va bene3")
                            var data = response.body()!!
                            withContext(Dispatchers.Main) {
                                Toast.makeText(view.context, "l'elemento è stato cancellato", Toast.LENGTH_SHORT).show()
                                text.text = ""
                            }
                        } else {
                            Log.d("mainActivityPro", response.code().toString())
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Log.e("MainActivity", e.toString())
                        }
                    }
                }
                //rendo invisibile l'elemento cancellato
                this.itemView.visibility = View.INVISIBLE
            }
        }

        init {
            view.setOnLongClickListener(this)
            textView0 = view.findViewById(R.id.idPrenotazione)
            textView1 = view.findViewById(R.id.textview_simo1)
            textView2 = view.findViewById(R.id.textview_simo2)
            textView3 = view.findViewById(R.id.textview_simo3)
        }
    }

}