package welcome

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.esamepm.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import homeCuoco.MainActivityCuo
import homeGen.MainActivityGen
import homeProf.mainActivityPro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.Serializable


class viewModelMain: ViewModel() {
    //metodo che contiene la logica per il login
    fun Start(context: Context, binding: ActivityMainBinding) {

        //definisco tre intent diversi
        val intent_pro = Intent(context, mainActivityPro::class.java)
        val intent_cuo = Intent(context, MainActivityCuo::class.java)
        val intent_gen = Intent(context, MainActivityGen::class.java)
        if (validatore.ValidazioneCredenziali(binding.editTextUserName.text.toString(), binding.editTextPassword.text.toString())) {
            val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

            val api = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2/progettolaurea/")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(ApiRequests::class.java)
            //rendo visibile la progress bar
            binding.progressBar.visibility = View.VISIBLE
            // creo la Post per la mia richiesta
            val myPost: logPost = logPost(binding.editTextUserName.text.toString(), binding.editTextPassword.text.toString())

            // faccio partire una coroutine per fare la richiesta di login al server

            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = api.Login(myPost)
                    //se la risposta è positiva assegno la risposta ad una variabile
                    if (response.isSuccessful) {
                        val data: loginResponse = response.body()!!
                        withContext(Dispatchers.Main) {
                            //all'interno del thread principale azzero i campi di input e rendo invisibile la progress bar
                            binding.editTextPassword.text.clear()
                            binding.editTextUserName.text.clear()
                            binding.progressBar.visibility = View.INVISIBLE
                            //in base al tipo di utente verrà fatta partire una activity diversa con diverse funzionalità
                            when (data.user.tipo_dipendente) {
                                "pro" -> {
                                    intent_pro.putExtra("credenziali", data as Serializable)
                                    startActivity(context, intent_pro, null)
                                }
                                "cuoco" -> {
                                    intent_cuo.putExtra("credenziali", data as Serializable)
                                    startActivity(context, intent_cuo, null)
                                }
                                "gen" -> {
                                    intent_gen.putExtra("credenziali", data as Serializable)
                                    startActivity(context, intent_gen, null)
                                }
                                else -> {
                                    //Se la risposta del server è positiva, ma l'utente non rientra tra quelli che possono usare l'app, come ad esempio il responsabile,
                                    // viene lanciato uno ShowDialog per avvisarlo

                                        binding.progressBar.visibility = View.INVISIBLE
                                        AlertDialog.Builder(context)
                                                .setTitle("Attenzione")
                                                .setMessage("Sei un utente non autorizzato ad usare l'app")
                                                .setPositiveButton("Ok", DialogInterface.OnClickListener() { dialog, which -> dialog.dismiss() })
                                                .create().show()
                                }
                            }
                        }
                    } else {
                        //Se la risposta contiene un errore significa che alcuni campi non sono stati compilati correttamente, quindi si avvisa l'utente con un alert
                        withContext(Dispatchers.Main) {
                            binding.progressBar.visibility = View.INVISIBLE
                            AlertDialog.Builder(context)
                                    .setTitle("Si è verificato un problema")
                                    .setMessage("Hai inserito dei dati non corretti, riprova")
                                    .setPositiveButton("Ok", DialogInterface.OnClickListener() { dialog, which -> dialog.dismiss() })
                                    .create().show()
                        }
                    }
                } catch (e: Exception) {
                    //Se ci sono altri tipi di errori si avvisa l'utente con un toast
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.INVISIBLE
                        Log.e("MainActivity", e.toString())
                        Toast.makeText(context, "Errore nell'operazione di autenticazione", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        else Toast.makeText(context, "Devi inserire delle credenziali per effettuare il login", Toast.LENGTH_LONG).show()
    }
}