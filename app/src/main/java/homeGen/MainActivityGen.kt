package homeGen

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import com.example.esamepm.R
/*
Questa classe rappresenta l'activity del dipendente di tipo 'generico'
 */
class MainActivityGen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_gen)
        //anche in questo caso viene definitauna funzione alternativa per il tasto back
        var callback = onBackPressedDispatcher.addCallback {
            back()
        }
    }
    // funzione alternativa per il tasto back
    fun back() {
        AlertDialog.Builder(this)
            .setTitle("Attenzione")
            .setMessage("premi il tasto Logout per tornare indietro")
            .setPositiveButton("Ok", DialogInterface.OnClickListener() { dialog, which -> dialog.dismiss() })
            .create().show()
    }
}