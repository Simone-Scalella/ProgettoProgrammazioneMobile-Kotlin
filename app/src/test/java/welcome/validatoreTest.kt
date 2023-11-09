package welcome

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
/*
Questi sono Test generici fatti su alcune componenti di controllo dell'app
 */
@RunWith(JUnit4::class)
class validatoreTest{
    @Test
    fun testDatiInput(){
        val userTest = ""
        val passwordTest = ""
        val risultato = validatore.ValidazioneCredenziali(userTest,passwordTest)
        assertFalse(risultato)
    }
    @Test
    fun testCF(){
        val cfTest = "SZQJCZ64E41A210R"
        val risultato = validatore.ValidazioneCF(cfTest)
        assertFalse(risultato)
    }
    @Test
    fun testCodMacchina(){
        val codMacchinaTest = 0
        val risultato = validatore.ValidazioneCodMacchina(codMacchinaTest)
        assertTrue(risultato)
    }
}