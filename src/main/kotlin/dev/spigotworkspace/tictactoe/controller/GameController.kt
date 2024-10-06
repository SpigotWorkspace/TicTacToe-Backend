package dev.spigotworkspace.tictactoe.controller

import dev.spigotworkspace.tictactoe.pojo.BaseResult
import dev.spigotworkspace.tictactoe.pojo.Game
import dev.spigotworkspace.tictactoe.pojo.GameResult
import dev.spigotworkspace.tictactoe.pojo.enum.PlayerEnum
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils

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
    @MessageMapping("/disconnect/{gameId}")
    @SendTo("/game/click/{gameId}")
    fun disconnect(@Header simpSessionId: String, @DestinationVariable gameId: String): BaseResult<GameResult> {
        val game = currentGames[gameId] ?: return BaseResult.failure("Game '$gameId' does not exist")
        if(playerToGameId[simpSessionId] != gameId) {
            return BaseResult.failure("Player is not part of the game '$gameId'")
        }
        val gameResult = game.result;

        gameResult.cancelled = true

        //TODO: Delete from currentGames and playerToGameId

        return BaseResult.success(gameResult)
    }

    @MessageMapping("/click/{gameId}")
    @SendTo("/game/click/{gameId}")
    fun handleClick(@Header simpSessionId: String, @DestinationVariable gameId: String, index: Int): BaseResult<GameResult> {
        val game = currentGames[gameId] ?: return BaseResult.failure("Game '$gameId' does not exist")
        if(playerToGameId[simpSessionId] != gameId) {
            return BaseResult.failure("Player is not part of the game '$gameId'")
        }

        val gameResult = game.result;
        if (!game.isPlayerOnTurn(simpSessionId)) {
            return BaseResult.success(gameResult)
        }

        gameResult.field.apply {
            if (!StringUtils.hasText(this[index])) {
                val char: String = game.playerOnTurn.sign
                this[index] = char
                game.switchTurn()
            }
        }

        checkWinner(gameResult)

        return BaseResult.success(gameResult)
    }

    fun checkWinner(gameResult: GameResult) {
        val field = gameResult.field
        val winCombinations = arrayOf(
            arrayOf(0, 1, 2),
            arrayOf(3, 4, 5),
            arrayOf(6, 7, 8),
            arrayOf(0, 3, 6),
            arrayOf(1, 4, 7),
            arrayOf(2, 5, 8),
            arrayOf(0, 4, 8),
            arrayOf(2, 4, 6)
        )

        for (combination in winCombinations) {
            val (a, b, c) = combination
            if (field[a].isNotEmpty() && field[a] == field[b] && field[a] == field[c]) {
                gameResult.setWinner(field[a])
            }
        }
    }

    private fun generateGameId(): String {
        return (1..9)
            .map { allowedChars.random() }
            .joinToString("")
    }
}