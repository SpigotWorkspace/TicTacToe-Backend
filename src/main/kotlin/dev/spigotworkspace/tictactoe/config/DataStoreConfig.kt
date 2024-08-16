package dev.spigotworkspace.tictactoe.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataStoreConfig {

    @Bean
    fun currentGames(): HashMap<String, String> {
        return HashMap();
    }
}