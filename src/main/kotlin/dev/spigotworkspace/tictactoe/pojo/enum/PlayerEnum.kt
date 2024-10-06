package dev.spigotworkspace.tictactoe.pojo.enum

enum class PlayerEnum(val sign: String) {
    ONE("X"),
    TWO("O");

    companion object {
        fun fromSign(sign: String): PlayerEnum? {
            return entries.find { it.sign == sign }
        }
    }

}