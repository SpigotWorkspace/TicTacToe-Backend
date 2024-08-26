package dev.spigotworkspace.tictactoe.config

import dev.spigotworkspace.tictactoe.pojo.Game
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataStoreConfig {

    @Bean
    fun currentGames(): HashMap<String, Game> {
        return HashMap()
    }
}