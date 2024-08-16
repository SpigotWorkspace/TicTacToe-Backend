package dev.spigotworkspace.tictactoe.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class GameController @Autowired constructor(private val currentGames: HashMap<String, String>) {
    private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

    @MessageMapping("/createGame")
    @SendTo("/game/createGame")
    fun place(@Header simpSessionId: String): String {
        var gameId: String
        var unique: Boolean
        do {
            gameId = generateGameId();
            unique = !currentGames.containsKey(gameId);
        } while (!unique)

        currentGames[gameId] = "test"

        return gameId;
    }

    private fun generateGameId(): String {
        return (1..1)
            .map { allowedChars.random() }
            .joinToString("")
    }
}