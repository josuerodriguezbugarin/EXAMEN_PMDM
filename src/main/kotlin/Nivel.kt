package org.example

enum class Nivel(val codigo: Int, val descripcion: String) { //enum class con los niveles
    BARBON(0, "Avenida G. Barbón"),
    COMPOSTELA(1, "Plaza de Compostela"),
    EN_MOVIMIENTO(2, "Mecanismo en movimiento"),
    ERROR(-1, "Error");
    companion object {
        fun desdeCodigo(codigo: Int): Nivel {
            return entries.find { it.codigo == codigo } ?: ERROR
        }
    }
}