package homeProf

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import androidx.activity.addCallback
import com.example.esamepm.R
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import welcome.ApiRequests
import welcome.loginResponse


class mainActivityPro : AppCompatActivity() {

    val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    val api = Retrofit.Builder()
            .baseUrl("http://10.0.2.2/progettolaurea/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiRequests::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //recupero le informazioni passate dall'activity precedente
        var Item = intent.extras
        var credenziali = Item?.get("credenziali") as loginResponse
        //defnisco una differente funzione del tasto back
        var callback = onBackPressedDispatcher.addCallback {
            back()
        }
        //definisco una variabile di countDown
        var countDownTimer = object : CountDownTimer(1000 * 10, 1000) {

            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                // allo scadere dei 10 secondi viene fatta una chiamata al server per controllare se l'utente ha ricevuto nuovi messaggi
                chiamata(credenziali)
                this.start()
            }
        }
        setContentView(R.layout.activity_main_prof)
        countDownTimer.start()

    }
    //funzione alternativa per il tasto back
    fun back() {
        AlertDialog.Builder(this)
                .setTitle("Attenzione")
                .setMessage("premi il tasto Logout per tornare indietro")
                .setPositiveButton("Ok", DialogInterface.OnClickListener() { dialog, which -> dialog.dismiss() })
                .create().show()
    }

    /*
    questa funzione è stata implementata e pensata come una primitiva della notifica, ogni 10 secondi si fa una richiesta al server, e nel caso in cui ci siano nuovi messaggi viene
    cambiato il colore del bottone 'Messaggi'
     */
    fun chiamata(credenziali: loginResponse){
        val token = credenziali.token
        val impostazioni =  "applicatio/json"
        Log.d("mainActivityPro","va bene1")
        GlobalScope.launch(Dispatchers.IO) {
            Log.d("mainActivityPro","va bene2")
            try {
                Log.d("mainActivityPro","va bene3")
                val response = api.MessaggiNOEliminazioneRequest(impostazioni,"Bearer "+token)
                var bottone = findViewById<Button>(R.id.messaggi)
                if (response.isSuccessful) {
                    Log.d("mainActivityPro","va bene3")
                    withContext(Dispatchers.Main){
                            Log.d("mainActivityPro","ci sono messaggi")
                            bottone?.setBackgroundColor(Color.RED)
                        }
                    }
                else {
                    Log.d("mainActivityPro","non ci sono messaggi")
                    bottone?.setBackgroundColor(Color.BLUE)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("MainActivity", e.toString())
                }
            }
        }
    }
}