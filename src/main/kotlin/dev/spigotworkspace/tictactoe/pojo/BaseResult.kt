package dev.spigotworkspace.tictactoe.pojo

import dev.spigotworkspace.tictactoe.pojo.enum.ResultStatus
class BaseResult<T>(
    var resultStatus: ResultStatus,
    var resultValue: T?,
    var errorMessage: String? = null
) {
    companion object {
        fun <T> success(resultValue: T): BaseResult<T> {
            return BaseResult(ResultStatus.SUCCESS, resultValue)
        }

        fun <T> failure(errorMessage: String): BaseResult<T> {
            return BaseResult(ResultStatus.FAILURE, null, errorMessage)
        }
    }
}