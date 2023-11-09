package homeProf.magazzino.lista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esamepm.R
/*
In questa classe è definita la RecycleView che visualizza i dati restituiti dal server
 */
class CustomAdapterListaMagazzino(private val data: List<Articoli>) : RecyclerView.Adapter<CustomAdapterListaMagazzino.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent,false)
        return ViewHolder(rowItem)
    }
//qui vengono valorizzate le textView del fragment
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var lunghezza = data[position].toString().length
        holder.textView.text ="   "+ data[position].toString().subSequence(21,lunghezza-1)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView

        init {
            textView = view.findViewById(R.id.textview_simo)
        }
    }

}