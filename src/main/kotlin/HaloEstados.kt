package org.example

import org.example.HaloDeVigo.currentState
import org.example.HaloDeVigo.nivel_actual
import org.example.HaloDeVigo.botonPulsado

sealed class HaloEstados {
    object Idle : HaloEstados() { //Nivel 0
        fun onEnter() {
            println("Estado Idle, nivel actual: $nivel_actual")
            when (botonPulsado) {
                Boton.nivel_1 -> {
                    if (nivel_actual == 0) {
                        println("Iniciando subida")
                        nivel_actual = Nivel.EN_MOVIMIENTO.codigo
                        currentState = MovingUP
                    } else {
                        println("Piso 1, abriendo puertas")
                        botonPulsado = null
                    }
                }
                Boton.nivel_0 -> {
                    if (nivel_actual == 1) {
                        println("Pulsado nivel 0 desde nivel 1, Iniciando bajada")
                        nivel_actual = Nivel.EN_MOVIMIENTO.codigo
                        currentState = MovingDOWN
                    } else {
                        println("No se realiza movimiento, ya esta en el piso 0")
                        botonPulsado = null
                    }
                }
                null -> println("En espera seleccione un nivel")
            }
        }
        override fun toString(): String = "Idle(nivel_actual=$nivel_actual)"
    }
    object MovingUP : HaloEstados() { // Estado en el que se encuentre subiendo
        fun onEnter() {
            println("Entrando a estado MovingUP. Subiendo de Nivel 0 a Nivel 1...")
            nivel_actual = Nivel.EN_MOVIMIENTO.codigo
            nivel_actual = Nivel.COMPOSTELA.codigo // Llegada a destino
            botonPulsado = null
            println("Llegada completada a Plaza de Compostela (Nivel 1).")
            currentState = Idle
        }
        override fun toString(): String = "MovingUP(nivel_actual=$nivel_actual)"
    }
    object MovingDOWN : HaloEstados() {// Estado en el que se encuentre bajando
        fun onEnter() {
            println("Bajando de nivel 1 a nivel 0")
            nivel_actual = Nivel.EN_MOVIMIENTO.codigo
            nivel_actual = Nivel.BARBON.codigo // Llegada a destino
            botonPulsado = null
            println("Llegada completada a Avenida G. Barbón (Nivel 0).")
            currentState = Idle
        }
        override fun toString(): String = "MovingDOWN(nivel_actual=$nivel_actual)"
    }
    object Error : HaloEstados() { //Estado de error
        fun onEnter() {
            println("Estado de Error.")
            nivel_actual = Nivel.ERROR.codigo
            botonPulsado = null
        }
        override fun toString(): String = "Error(nivel_actual=$nivel_actual)"
    }
}