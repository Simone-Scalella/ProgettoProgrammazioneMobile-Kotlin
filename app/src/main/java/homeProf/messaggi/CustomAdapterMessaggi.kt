package homeProf.messaggi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.esamepm.R
/*
In questa classe definisco la recycleView per gestire la risposta del server quando vengono richiesti i messaggi che un utente deve leggere
*/
class CustomAdapterMessaggi(private val data: List<MessaggiResponse>) : RecyclerView.Adapter<CustomAdapterMessaggi.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItem: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
        return ViewHolder(rowItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView1.text = data[position].messaggio
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView1: TextView

        init {
            textView1 = view.findViewById(R.id.textview_simo)
        }
    }

}