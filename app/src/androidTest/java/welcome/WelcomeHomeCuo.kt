package welcome

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.esamepm.R
import homeCuoco.MainActivityCuo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
/*
Test sulla UI dell'app, viene simulata una situazione di inserimento da parte del cuoco di una richiesta per un oggetto del magazzino
 */
@RunWith(AndroidJUnit4::class)
class WelcomeHomeCuo {

    private lateinit var cuoco: ActivityScenario<MainActivityCuo>

    @Before
    fun setup(){

        cuoco = ActivityScenario.launch(MainActivityCuo::class.java)
        cuoco.moveToState(Lifecycle.State.RESUMED)
    }
    @Test
    fun TestHomeCuo(){
        var TestInput1 = "forbici"
        var TestInput2 = "10"
        var TestInput3 = "2020"
        var TestInput4 = "09"
        var TestInput5 = "10"
        onView(withId(R.id.richiesta_magazzino_cuoco)).perform(ViewActions.click())
        onView(withId(R.id.inputArticolo)).perform(ViewActions.typeText((TestInput1)))
        onView(withId(R.id.InputQuantita)).perform(ViewActions.typeText((TestInput2)))
        onView(withId(R.id.anno)).perform(ViewActions.typeText((TestInput3)))
        onView(withId(R.id.mese)).perform(ViewActions.typeText((TestInput4)))
        onView(withId(R.id.giorno)).perform(ViewActions.typeText((TestInput5)))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.buttonRichiestaMagazzino)).perform(ViewActions.click())
        Espresso.pressBack()
    }
}