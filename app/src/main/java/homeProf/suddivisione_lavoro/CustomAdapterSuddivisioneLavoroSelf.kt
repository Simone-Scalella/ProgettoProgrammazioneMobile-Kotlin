package homeProf.suddivisione_lavoro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esamepm.R
/*
In questa classe viene definita la RecycleView per gestire la risposta del server quando vengono richieste le proprie suddivisioni lavoro
 */
class CustomAdapterSuddivisioneLavoroSelf(private val data: List<SelfSuddLavoroResponse>) : RecyclerView.Adapter<CustomAdapterSuddivisioneLavoroSelf.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view3, parent, false)
        return ViewHolder(rowItem)
    }
//qui vengono valorizzate le TextView della lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView1.text = "Comm. " +data[position].commessa?.codice_merce
        holder.textView2.text = "Q."+data[position].quantita_assegnata + " unita'"
        if(data[position].data_conclusione == null) {
                holder.textView3.text = "data null"
            }
        else {holder.textView3.text = "data "+data[position].data_conclusione }
        holder.textView4.text = data[position].valore_lavoro + " $"
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView1: TextView
        val textView2: TextView
        val textView3: TextView
        val textView4: TextView

        init {
            textView1 = view.findViewById(R.id.textview_simoSL1)
            textView2 = view.findViewById(R.id.textview_simoSL2)
            textView3 = view.findViewById(R.id.textview_simoSL3)
            textView4 = view.findViewById(R.id.textview_simoSL4)
        }
    }

}