package homeProf.magazzino.ListaRichieste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esamepm.R
/*
In questa classe è definita la RecycleView per gestire il risultato della richiesta al server
*/
class CustomAdapterSelfAggiornamento(private val data: List<SelfAggiornamentoResponse>) : RecyclerView.Adapter<CustomAdapterSelfAggiornamento.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view2, parent, false)
        return ViewHolder(rowItem)
    }
//vengono valorizzate le textView della lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView1.text = data[position].magazzino
        holder.textView2.text = "Qtà: "+data[position].quantita.toString()
        holder.textView3.text = "Data: "+data[position].data.toString().subSequence(0,10)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView1: TextView
        val textView2: TextView
        val textView3: TextView

        init {
            textView1 = view.findViewById(R.id.textview_simo1)
            textView2 = view.findViewById(R.id.textview_simo2)
            textView3 = view.findViewById(R.id.textview_simo3)
        }
    }

}