package dev.spigotworkspace.tictactoe.pojo

import dev.spigotworkspace.tictactoe.pojo.enum.PlayerEnum

class Game(private var playerOne: String){
    private lateinit var playerTwo: String

    fun setPlayer(playerEnum: PlayerEnum, id: String) {
        when (playerEnum) {
            PlayerEnum.ONE -> playerOne = id
            PlayerEnum.TWO -> playerTwo = id
        }
    }
}

