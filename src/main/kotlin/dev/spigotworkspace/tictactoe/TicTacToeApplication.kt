package dev.spigotworkspace.tictactoe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer


@SpringBootApplication
class TicTacToeApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
	runApplication<TicTacToeApplication>(*args)
}
