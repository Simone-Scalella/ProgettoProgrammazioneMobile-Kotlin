package homeProf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.esamepm.R
import welcome.loginResponse
/*
In questa classe sono definiti tutti i bottoni visualizzati dall'utente professionale, con le corrispondenti azioni
da compiere quando vengono premuti
 */
class welcomeProf : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.welcome_prof_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //recupero le credenziali passate dall'activity precedente, queste informazioni vengono passate come argomento ad ogni azione
        val Item = activity?.intent?.extras
        var credenziali = Item?.get("credenziali") as loginResponse
        var bottoneListaMagazzino = view?.findViewById<Button>(R.id.vista_magazzino)
        val bottone_logout = view?.findViewById<Button>(R.id.LogOut)
        var bottoneRichiestaMagazzino = view?.findViewById<Button>(R.id.richiesta_magazzino)
        var bottoneListaRichiesteMagazzino = view?.findViewById<Button>(R.id.lista_richieste_magazzino)
        var bottoneListaSelfPrenotazioni = view?.findViewById<Button>(R.id.lista_prenotazione_macchina)
        var bottoneSuddivisioneLavoro = view?.findViewById<Button>(R.id.Suddivisioni_lavoro)
        var bottoneAnagrafiche = view?.findViewById<Button>(R.id.anagrafiche_dipendente)
        var bottonePrenotazioniGeneriche = view?.findViewById<Button>(R.id.prenatozioni_macchinario)
        var bottoneRichiesteLavoro = view?.findViewById<Button>(R.id.Lista_lavori)
        var bottoneMessaggi = view?.findViewById<Button>(R.id.messaggi)
        var bottoneInserisciPre = view?.findViewById<Button>(R.id.Prenotazione_macchina)
        var bottoneInserisciTrasf = view?.findViewById<Button>(R.id.Nuovo_lavoro)
        bottoneListaMagazzino?.setOnClickListener() {
            Navigation.findNavController(requireView()).navigate(R.id.vedere_listaMagazzino, Item)
        }
        bottoneRichiestaMagazzino?.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.inviare_richiestaMagazzino,Item)
        }
        bottone_logout?.setOnClickListener(){
            System.exit(1)
            activity?.finish()
        }
        bottoneListaRichiesteMagazzino?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_welcomeProf_to_SelfFragmentAggiornamento,Item)
        }
        bottoneListaSelfPrenotazioni?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_welcomeProf_to_listaSelfPrenotazioni,Item)
        }
        bottoneSuddivisioneLavoro?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_welcomeProf_to_listaSelfSuddivisioneLavoro,Item)
        }
        bottoneAnagrafiche?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_welcomeProf_to_AnagraficheDipendente,Item)
        }
        bottonePrenotazioniGeneriche?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_welcomeProf_to_PrenotazioniGeneriche,Item)
        }
        bottoneRichiesteLavoro?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_welcomeProf_to_RichiesteLavoro,Item)
        }
        bottoneMessaggi?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_welcomeProf_to_Messaggi_utente,Item)
        }
        bottoneInserisciPre?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_welcomeProf_to_InserisciPre,Item)
        }
        bottoneInserisciTrasf?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_welcomeProf_to_InserisciTrasferimento,Item)
        }
    }
}