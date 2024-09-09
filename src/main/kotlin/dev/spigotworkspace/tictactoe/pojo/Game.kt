package dev.spigotworkspace.tictactoe.pojo

import dev.spigotworkspace.tictactoe.pojo.enum.PlayerEnum

class Game(private var playerOne: String?){
    private var playerTwo: String? = null

    fun setPlayer(playerEnum: PlayerEnum, id: String?) {
        when (playerEnum) {
            PlayerEnum.ONE -> playerOne = id
            PlayerEnum.TWO -> playerTwo = id
        }
    }

    fun isFull(): Boolean {
        return playerOne != null && playerTwo != null
    }

    fun isPlayerOneInGame(): Boolean {
        return playerOne != null
    }
}

