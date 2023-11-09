package welcome

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.esamepm.R
import com.example.esamepm.databinding.ActivityMainBinding
/*
Prima activity dell'app
 */
class MainActivity : AppCompatActivity() {
//Funzione per far abbassare la tastiera
    fun hideKeyBoard(){
        val view = this.currentFocus
        if(view != null){
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken,0)
        }
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    val viewModel: viewModelMain by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // creo un binding per gestire meglio gli elementi dell'activity
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.imageButton.setOnClickListener() {
            //abbasso la tastiera dopo aver premuto il tasto per il Login
            hideKeyBoard()
            //chiamo il metodo per far partire la logica di login dell'app
            viewModel.Start(this,binding)
        }
    }
}