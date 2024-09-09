package dev.spigotworkspace.tictactoe.pojo

import dev.spigotworkspace.tictactoe.pojo.enum.ResultStatus
class BaseResult(
    var resultStatus: ResultStatus,
    var resultValue: String?,
    var errorMessage: String? = null
) {
    companion object {
        fun success(resultValue: String): BaseResult {
            return BaseResult(ResultStatus.SUCCESS, resultValue)
        }

        fun failure(errorMessage: String): BaseResult {
            return BaseResult(ResultStatus.FAILURE, null, errorMessage)
        }
    }
}