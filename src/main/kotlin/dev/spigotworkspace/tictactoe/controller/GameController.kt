package dev.spigotworkspace.tictactoe.controller

import dev.spigotworkspace.tictactoe.pojo.Game
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class GameController @Autowired constructor(private val currentGames: HashMap<String, Game>) {
    private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    private val logger = LoggerFactory.getLogger(GameController::class.java)

    @MessageMapping("/createGame")
    @SendTo("/game/createGame")
    fun createGame(@Header simpSessionId: String): String {
        var gameId: String
        var unique: Boolean
        do {
            gameId = generateGameId()
            unique = !currentGames.containsKey(gameId)
        } while (!unique)

        currentGames[gameId] = Game(simpSessionId)

        logger.debug("Created Game {}", gameId)
        return gameId
    }

    @MessageMapping("/joinGame")
    @SendTo("game/joinGame")
    fun joinGame(@Header simpSessionId: String, gameId: String) {
        //TODO
    }

    private fun generateGameId(): String {
        return (1..9)
            .map { allowedChars.random() }
            .joinToString("")
    }
}