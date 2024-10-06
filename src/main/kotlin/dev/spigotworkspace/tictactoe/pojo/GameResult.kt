package dev.spigotworkspace.tictactoe.pojo

class GameResult {
    var winner: String? = null
        private set
    var cancelled: Boolean = false;
    val field: Array<String> = Array(9) { "" }


    fun setWinner(winner: String) {
        this.winner = winner
        this.cancelled = true
    }
}