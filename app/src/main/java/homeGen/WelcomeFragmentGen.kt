package homeGen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.esamepm.R
import welcome.loginResponse
/*
In questa classe sono definiti tutti i bottoni visualizzati dall'utente generico, con le corrispondenti azioni
da compiere quando vengono premuti
 */
class WelcomeFragmentGen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_gen, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //recupero le credenziali passate dall'activity precedente, queste informazioni vengono passate come argomento ad ogni azione
        val Item = activity?.intent?.extras
        var bottoneListaMagazzino = view?.findViewById<Button>(R.id.vista_magazzino_gen)
        val bottone_logout = view?.findViewById<Button>(R.id.LogOut_gen)
        var bottoneRichiestaMagazzino = view?.findViewById<Button>(R.id.richiesta_magazzino_gen)
        var bottoneListaRichiesteMagazzino = view?.findViewById<Button>(R.id.lista_richieste_magazzino_gen)
        var bottoneAnagrafiche = view?.findViewById<Button>(R.id.anagrafiche_gen)
        var bottoneInserisciSuddivisione = view?.findViewById<Button>(R.id.Inserisci_sudd_lavoro)
        var bottoneSuddLavCF = view?.findViewById<Button>(R.id.sudd_lavoro_vis_elim)
        bottoneListaMagazzino?.setOnClickListener() {
            Navigation.findNavController(requireView()).navigate(R.id.action_Welcom_gen_to_listaMagazzino_gen, Item)
        }
        bottoneRichiestaMagazzino?.setOnClickListener() {
            Navigation.findNavController(requireView()).navigate(R.id.action_Welcom_gen_to_richiestaMagazzinoFragment_gen, Item)
        }
        bottoneListaRichiesteMagazzino?.setOnClickListener() {
            Navigation.findNavController(requireView()).navigate(R.id.action_Welcom_gen_to_SelfFragmentAggiornamento_gen, Item)
        }
        bottoneAnagrafiche?.setOnClickListener() {
            Navigation.findNavController(requireView()).navigate(R.id.action_Welcom_gen_to_AnagraficheGenerico, Item)
        }
        bottone_logout?.setOnClickListener(){
            System.exit(1)
            activity?.finish()
        }
        bottoneInserisciSuddivisione?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_Welcom_gen_to_InserireSuddLav, Item)
        }
        bottoneSuddLavCF?.setOnClickListener(){
            Navigation.findNavController(requireView()).navigate(R.id.action_Welcom_gen_to_SuddLavCF_gen, Item)
        }
    }
}