package homeGen.Sudd_lavoro.ListaCF

import android.app.AlertDialog
import android.content.DialogInterface
import android.opengl.Visibility
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
import homeProf.suddivisione_lavoro.SelfSuddLavoroResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import welcome.ApiRequests
/*
In questa classe andiamo a definire la recycleView che deve gestire la risposta del server, quando vengono richieste le suddivisioni lavoro di un dipendente
 */
class CustomAdapterListaCF(private val data: List<SelfSuddLavoroResponse>,var token: String) : RecyclerView.Adapter<CustomAdapterListaCF.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view3, parent, false)
        return ViewHolder(rowItem)
    }
//Qui vengono valorizzate le textView della lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView1.text = "Comm. " +data[position].commessa?.codice_merce
        holder.textView2.text = "Q."+data[position].quantita_assegnata + " unita'"
        if(data[position].data_conclusione == null) {
            holder.textView3.text = "data null"
        }
        else {holder.textView3.text = "data "+data[position].data_conclusione }
        holder.textView4.text = data[position].valore_lavoro + " $"
    //vengono valorizzate delle variabili necessarie per la cancellazione di un elemento
        holder._token = token
        holder.Commessa = data[position].commessa!!.codice_merce
        holder.CF = data[position].dipendente
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnLongClickListener{
        val textView1: TextView
        val textView2: TextView
        val textView3: TextView
        val textView4: TextView
        lateinit var _token: String
        lateinit var CF: String
        lateinit var Commessa: String
// qui viene definita la funzione da chiamare quando si verifica l'evento onLongClick
        override fun onLongClick(view: View?): Boolean {
            AlertDialog.Builder(view?.context)
                .setTitle("Eliminare questo elemento ?")
                .setMessage("Se premi ok l'elemento selezionato verrà eliminato")
                .setPositiveButton("Ok",
                    DialogInterface.OnClickListener(){ dialog, which -> EliminaElemento(view!!,Commessa,CF,_token,textView1,textView2,textView3,textView4) })//TextView(view.context)
                .setNegativeButton( "Annulla" , DialogInterface.OnClickListener(){ dialog, which -> dialog.dismiss() })
                .create().show()
            return true
        }
// questa è la funzione chiamata per cancellare un elemento
        fun EliminaElemento(view: View, CommessaDelete: String,DipendenteDelete: String ,tokenElim: String,testo1: TextView,testo2: TextView,testo3: TextView,testo4: TextView){
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val api = Retrofit.Builder()
                .baseUrl("http://10.0.2.2/progettolaurea/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ApiRequests::class.java)
//vengono fatti dei controlli su delle variabili per evitare di cancellare due volte lo stesso elemento
            if(testo1.text.toString().equals("")){
                Toast.makeText(view.context, "non puoi eliminare 2 volte lo stesso elemento", Toast.LENGTH_SHORT).show()
            }
            else {
                Log.d("msn",tokenElim)
                val myPost: DeleteSuddPost = DeleteSuddPost(CommessaDelete,DipendenteDelete)
                val impostazioni = "applicatio/json"
                Log.d("mainActivityPro", "va bene1")
                GlobalScope.launch(Dispatchers.IO) {
                    Log.d("mainActivityPro", "va bene2")
                    try {
                        Log.d("mainActivityPro", "va bene3")
                        val response = api.EliminaSuddivisioneRequest(impostazioni, "Bearer " + tokenElim, myPost)
            //qui viene fatta la richiesta al server
                        if (response.isSuccessful) {
                            Log.d("mainActivityPro", "va bene3")
                            var data = response.body()!!
                            withContext(Dispatchers.Main) {
                                //si avvisa l'utente che l'operazione è andata a buon fine
                                Toast.makeText(view.context, "l'elemento è stato cancellato", Toast.LENGTH_SHORT).show()
                                testo1.text = ""
                                testo2.text = "DELETE"
                                testo3.visibility =  View.INVISIBLE
                                testo4.visibility =  View.INVISIBLE
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
            }
        }

        init {
            view.setOnLongClickListener(this)
            textView1 = view.findViewById(R.id.textview_simoSL1)
            textView2 = view.findViewById(R.id.textview_simoSL2)
            textView3 = view.findViewById(R.id.textview_simoSL3)
            textView4 = view.findViewById(R.id.textview_simoSL4)
        }
    }

}