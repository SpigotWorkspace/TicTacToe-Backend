package dev.spigotworkspace.tictactoe.component

import dev.spigotworkspace.tictactoe.pojo.Game
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class WebSocketEventListener @Autowired constructor(private val currentGames: HashMap<String, Game>) {
    @EventListener
    fun handleSessionDisconnect(event: SessionDisconnectEvent) {
        //TODO: remove player and cancel game
    }

}