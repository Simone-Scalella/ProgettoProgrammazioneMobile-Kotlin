package homeProf.anagrafiche

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.esamepm.R
import welcome.loginResponse
/*
In questa classe vengono visualizzate tutte le informazioni dell'utente
 */
class AnagraficheFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anagrafiche, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//qui vengono valorizzate tutte le textView del fragment
        val credenziali = arguments?.get("credenziali") as loginResponse
        var vistaCF = view?.findViewById<TextView>(R.id.textViewshow_CF)
        var vistaNome = view?.findViewById<TextView>(R.id.textViewshow_Nome_Cogn)
        var vistaTipo = view?.findViewById<TextView>(R.id.textViewshow_TipoDip)
        var vistaData = view?.findViewById<TextView>(R.id.textViewshow_DataNascita)
        var vistaUser = view?.findViewById<TextView>(R.id.textViewshow_User)
        vistaCF?.text = credenziali.user.CF
        vistaNome?.text = credenziali.user.nome_cognome
        vistaUser?.text = credenziali.user.username
        vistaTipo?.text = credenziali.user.tipo_dipendente
        vistaData?.text = credenziali.user.data_di_nascita

    }
}