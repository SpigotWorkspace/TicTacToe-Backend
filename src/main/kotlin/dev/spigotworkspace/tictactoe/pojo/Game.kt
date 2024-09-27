package dev.spigotworkspace.tictactoe.pojo

import dev.spigotworkspace.tictactoe.pojo.enum.PlayerEnum

class Game(private var playerOne: String?){
    private var playerTwo: String? = null
    var playerOnTurn: PlayerEnum = PlayerEnum.ONE
    val field: Array<String> = Array(9) { "" }

    fun setPlayer(playerEnum: PlayerEnum, simpSessionId: String?) {
        when (playerEnum) {
            PlayerEnum.ONE -> playerOne = simpSessionId
            PlayerEnum.TWO -> playerTwo = simpSessionId
        }
    }

    fun isPlayerOnTurn(simpSessionId: String): Boolean {
        return when (playerOnTurn) {
            PlayerEnum.ONE -> playerOne == simpSessionId
            PlayerEnum.TWO -> playerTwo == simpSessionId
        }
    }

    fun switchTurn() {
        playerOnTurn = when (playerOnTurn) {
            PlayerEnum.ONE -> PlayerEnum.TWO
            PlayerEnum.TWO -> PlayerEnum.ONE
        }
    }

    fun isFull(): Boolean {
        return playerOne != null && playerTwo != null
    }

    fun isPlayerOneInGame(): Boolean {
        return playerOne != null
    }

}

