package welcome


import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.esamepm.R
import homeCuoco.MainActivityCuo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
/*
Test sulla UI dell'app, viene simulata una situazione di loggin dell'utente
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    private lateinit var test: ActivityScenario<MainActivity>
    private lateinit var cuoco: ActivityScenario<MainActivityCuo>
    private var bundle = Bundle()

    @Before
    fun setup(){

        test = ActivityScenario.launch(MainActivity::class.java)
        test.moveToState(Lifecycle.State.RESUMED)


    }
    @Test
    fun testHome(){
        onView(withId(R.id.imageButton)).perform(click())
        val username = "user_test"
        val password = "password_test"
        onView(withId(R.id.editTextUserName)).perform((ViewActions.typeText(username)))
        onView(withId(R.id.editTextPassword)).perform((ViewActions.typeText(password)))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.imageButton)).perform(click())
        bundle.putString("credenziali","Test")
        cuoco = ActivityScenario.launch(MainActivityCuo::class.java,bundle)
        cuoco.moveToState(Lifecycle.State.RESUMED)
        Espresso.pressBack()
    }
}