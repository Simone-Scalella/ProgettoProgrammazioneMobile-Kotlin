package homeProf.RichiesteLavoro

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
import homeProf.RichiesteLavoro.delete.DeleteTrasferimentoPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import welcome.ApiRequests
/*
In questa classe vado a defnire la recycleView per gestire la risposta del server dopo la richiesta di avere tutte le richieste di trasferimento lavoro.
Posso anche cancellare degli elementi se soddisfano un requisito
*/
class CustomAdapterSelfRichieste(private val data: List<SelfRichiesteLavoroResponse>, val token: String) : RecyclerView.Adapter<CustomAdapterSelfRichieste.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view4, parent, false)
        return ViewHolder(rowItem)
    }
    //vado a valorizzare le text view della lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView4.text = "Comm. " +data[position].commessa
        holder.textView1.text = "Q."+data[position].quantita_trasferita +" unita'"
        if(data[position].data_trasferimento == null) {
            holder.textView3.text = "data null"
        }
        else {holder.textView3.text = "Data: "+data[position].data_trasferimento }
        holder.textView2.text = data[position].valore_trasferito + " $"
        if(data[position].confermato.equals("0")){
            holder.textView5.text = "non confermato"
        }
        else holder.textView5.text = "confermato"
        holder.textView0.text = data[position].codice_trasf.toString()
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
        val textView4: TextView
        val textView5: TextView
        lateinit var _token: String
        //qui vado a defnire la funzione per l'evento onLongClick
        override fun onLongClick(view: View?): Boolean {
            //viene avvisato l'utente sulla possibilità di cancellare un elemento
            AlertDialog.Builder(view?.context)
                .setTitle("Eliminare questo elemento ?")
                .setMessage("Se premi ok l'elemento selezionato verrà eliminato")
                .setPositiveButton("Ok",
                        DialogInterface.OnClickListener() { dialog, which -> EliminaElemento(view!!, textView0, textView5.text.toString(), _token) })
                .setNegativeButton("Annulla", DialogInterface.OnClickListener() { dialog, which -> dialog.dismiss() })
                .create().show()
            return true
        }
        //funzione per la cancellazione
        fun EliminaElemento(view: View, text: TextView, conferma: String, tokenElim: String) {

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val api = Retrofit.Builder()
                .baseUrl("http://10.0.2.2/progettolaurea/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ApiRequests::class.java)
            //controlli per verificare se è possibile cancellare quell'elemento
            if (text.text.toString().equals("") || conferma.equals("confermato")) {
                Toast.makeText(
                        view.context,
                        "non puoi eliminare 2 volte lo stesso elemento o un elemento già confermato",
                        Toast.LENGTH_SHORT
                ).show()
            } else {
                //se i requisiti sono soddisfatti viene fatta la richiesta al server
                var codice_trasf = text.text.toString()
                Log.d("msn", codice_trasf.toString())
                Log.d("msn", tokenElim)
                val myPost: DeleteTrasferimentoPost = DeleteTrasferimentoPost(codice_trasf)
                val impostazioni = "applicatio/json"
                Log.d("mainActivityPro", "va bene1")
                GlobalScope.launch(Dispatchers.IO) {
                    Log.d("mainActivityPro", "va bene2")
                    try {
                        Log.d("mainActivityPro", "va bene3")
                        val response =
                            api.EliminaTrasferimentoRequest(impostazioni, "Bearer " + tokenElim, myPost)

                        if (response.isSuccessful) {
                            Log.d("mainActivityPro", "va bene3")
                            var data = response.body()!!
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                        view.context,
                                        "l'elemento è stato cancellato",
                                        Toast.LENGTH_SHORT
                                ).show()
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
            textView0 = view.findViewById(R.id.textViewData)
            textView1 = view.findViewById(R.id.textview_simoSR1)
            textView2 = view.findViewById(R.id.textview_simoSR2)
            textView3 = view.findViewById(R.id.textview_simoSR3)
            textView4 = view.findViewById(R.id.textview_simoSR4)
            textView5 = view.findViewById(R.id.textview_simoSR)

        }
    }

}