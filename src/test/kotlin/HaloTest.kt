package org.example

import org.example.HaloEstados.*
import org.testng.annotations.AfterTest
import org.testng.Assert.assertEquals
import org.testng.annotations.Test
import Boton

class HaloTest {

    @org.testng.annotations.BeforeTest
    fun resetBefore() {
        resetHalo()
    }

    @AfterTest
    fun resetAfter() {
        resetHalo()
    }

    @Test
    fun testEstadoInicial_DebeSerIdleYNivel0() {
        assertEquals(Idle, HaloDeVigo.currentState)
        assertEquals(0, HaloDeVigo.nivel_actual)
    }

    @Test
    fun testTransicion_Idle_A_MovingUP_AlPulsarNivel1(pulsarBoton: HaloDeVigo.(Boton) -> Unit) {
        HaloDeVigo.nivel_actual = 0
        HaloDeVigo.pulsarBoton(Boton.nivel_1)

        val siguienteEstado = HaloDeVigo.gestorEstados()

        assertEquals(MovingUP, siguienteEstado)
        assertEquals(2, HaloDeVigo.nivel_actual)
    }

    @Test
    fun testTransicion_Idle_A_MovingDOWN_AlPulsarNivel0(pulsarBoton: HaloDeVigo.(Boton) -> Unit) {
        HaloDeVigo.nivel_actual = 1
        HaloDeVigo.currentState = Idle
        HaloDeVigo.pulsarBoton(Boton.nivel_0)

        val siguienteEstado = HaloDeVigo.gestorEstados()

        assertEquals(MovingDOWN, siguienteEstado)
        assertEquals(2, HaloDeVigo.nivel_actual)
    }

    @Test
    fun testCompleto_SubidaYRetornoAIdle(pulsarBoton: HaloDeVigo.(Boton) -> Unit) {
        HaloDeVigo.nivel_actual = 0
        HaloDeVigo.pulsarBoton(Boton.nivel_1)

        // 1. Transicionar de Idle a MovingUP
        HaloDeVigo.gestorEstados()
        assertEquals(MovingUP, HaloDeVigo.currentState)

        // 2. Ejecutar MovingUP para completarla llegada a Nivel 1
        HaloDeVigo.gestorEstados()
        assertEquals(Idle, HaloDeVigo.currentState)
        assertEquals(1, HaloDeVigo.nivel_actual)
    }

    @Test
    fun testPermanecerEnIdle_SiSePulsaMismoNivel(pulsarBoton: HaloDeVigo.(Boton) -> Unit) {
        HaloDeVigo.nivel_actual = 0
        HaloDeVigo.pulsarBoton(Boton.nivel_0)

        val estado = HaloDeVigo.gestorEstados()

        assertEquals(Idle, estado)
        assertEquals(0, HaloDeVigo.nivel_actual)
    }

    @Test
    fun testTransicion_A_EstadoError() {
        HaloDeVigo.currentState = Error

        val estado = HaloDeVigo.gestorEstados()

        assertEquals(Error, estado)
        assertEquals(-1, HaloDeVigo.nivel_actual)
    }

    private fun resetHalo() {
        HaloDeVigo.nivel_actual = 0
        HaloDeVigo.botonPulsado = null
        HaloDeVigo.currentState = Idle
    }
}