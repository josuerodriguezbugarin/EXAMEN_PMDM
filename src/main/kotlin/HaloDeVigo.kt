package org.example

import org.example.HaloEstados.*
import Boton

object HaloDeVigo {//singleton que gestiona los estados
var nivel_actual: Int = 0
    var botonPulsado: Boton? = null
    var currentState: HaloEstados = Idle //Estado actual

    fun gestorEstados(): HaloEstados { //ejecuta el estado correspondiente
        println("Estado actual: $currentState")
        when (currentState) {
            is Idle -> (currentState as Idle).onEnter()
            is MovingUP -> (currentState as MovingUP).onEnter()
            is MovingDOWN -> (currentState as MovingDOWN).onEnter()
            is Error -> (currentState as Error).onEnter()
        }
        return currentState
    }
}