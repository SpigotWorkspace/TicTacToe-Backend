package dev.spigotworkspace.tictactoe.controller

import dev.spigotworkspace.tictactoe.pojo.BaseResult
import dev.spigotworkspace.tictactoe.pojo.Game
import dev.spigotworkspace.tictactoe.pojo.enum.PlayerEnum
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.stereotype.Controller

@Controller
class GameController @Autowired constructor(
    private val currentGames: HashMap<String, Game>,
    private val playerToGameId: HashMap<String, String>
) {
    private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    private val logger = LoggerFactory.getLogger(GameController::class.java)

    @MessageMapping("/createGame")
    @SendToUser("/game/createGame")
    fun createGame(@Header simpSessionId: String): String {
        var gameId: String
        var unique: Boolean
        do {
            gameId = generateGameId()
            unique = !currentGames.containsKey(gameId)
        } while (!unique)

        currentGames[gameId] = Game(simpSessionId)
        playerToGameId[simpSessionId] = gameId

        logger.debug("Created Game {}", gameId)
        return gameId
    }

    @MessageMapping("/joinGame")
    @SendToUser("/game/joinGame")
    fun joinGame(@Header simpSessionId: String, gameId: String): BaseResult<String> {
        val game = currentGames[gameId] ?: return BaseResult.failure("Game '$gameId' does not exist")

        if (game.isFull()) {
            return BaseResult.failure("Game '$gameId' is already full")
        } else if (!game.isPlayerOneInGame()) {
            currentGames.remove(gameId)
            return BaseResult.failure("Game '$gameId' does not exist")
        }

        game.setPlayer(PlayerEnum.TWO, simpSessionId)
        playerToGameId[simpSessionId] = gameId

        return BaseResult.success(gameId)
    }
    @MessageMapping("/disconnect")
    @SendToUser("/game/disconnect")
    fun disconnect(@Header simpSessionId: String) {
        //TODO
    }

    @MessageMapping("/click/{gameId}")
    @SendTo("/game/click/{gameId}")
    fun handleClick(@Header simpSessionId: String, @DestinationVariable gameId: String, index: Int): BaseResult<Array<String>> {
        val game = currentGames[gameId] ?: return BaseResult.failure("Game '$gameId' does not exist")
        val field = game.getField().apply { this[index] = "X" }
        return BaseResult.success(field);
    }

    private fun generateGameId(): String {
        return (1..9)
            .map { allowedChars.random() }
            .joinToString("")
    }
}