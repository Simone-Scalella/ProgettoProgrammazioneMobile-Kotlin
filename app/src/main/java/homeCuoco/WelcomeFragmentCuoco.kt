package homeCuoco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.esamepm.R
import welcome.loginResponse
import welcome.user

/*
In questa classe sono definiti tutti i bottoni visualizzati dall'utente cuoco, con le corrispondenti azioni
da compiere quando vengono premuti
 */
class WelcomeFragmentCuoco : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome_cuoco, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var Item = activity?.intent?.extras

        //operazione necessaria per eseguire un UI Test
        if(Item == null){
            var user_test = user("Codice Fiscale Test","nome e cognome di test","dipendente test",
                    2.50f,2.30f,1.20f,"123456789","userTest",
                    "1990/05/20")
            var logRespTest = loginResponse("token_test",user_test)
            Item?.putSerializable("credenziali",logRespTest)
        }
        //recupero le credenziali passate dall'activity precedente, queste informazioni vengono passate come argomento ad ogni azione
        var bottoneListaMagazzino = view?.findViewById<Button>(R.id.vista_magazzino_cuoco)
        val bottone_logout = view?.findViewById<Button>(R.id.LogOut_cuoco)
        var bottoneRichiestaMagazzino = view?.findViewById<Button>(R.id.richiesta_magazzino_cuoco)
        var bottoneListaRichiesteMagazzino = view?.findViewById<Button>(R.id.lista_richieste_magazzino_cuoco)
        var bottoneAnagrafiche = view?.findViewById<Button>(R.id.anagrafiche_cuoco)
        bottoneListaMagazzino?.setOnClickListener() {
            Navigation.findNavController(requireView()).navigate(R.id.action_WelcomeCuoco_to_listaMagazzino, Item)
        }
        bottoneRichiestaMagazzino?.setOnClickListener() {
            Navigation.findNavController(requireView()).navigate(R.id.action_WelcomeCuoco_to_richiestaMagazzinoFragment, Item)
        }
        bottoneListaRichiesteMagazzino?.setOnClickListener() {
            Navigation.findNavController(requireView()).navigate(R.id.action_WelcomeCuoco_to_SelfFragmentAggiornamento, Item)
        }
        bottoneAnagrafiche?.setOnClickListener() {
            Navigation.findNavController(requireView()).navigate(R.id.action_WelcomeCuoco_to_AnagraficheDipendente, Item)
        }
        bottone_logout?.setOnClickListener(){
            System.exit(1)
            activity?.finish()
        }
    }
}