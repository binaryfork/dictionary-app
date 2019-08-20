package com.bf.dict.domain.network.yandex


object ErrorCodes {
    val INVALID_API_KEY = 401
    val BLOCKED_API_KEY = 402
    val DAILY_LIMIT_EXCEEDED = 404
    val TEXT_LENGTH_LIMIT_EXCEEDED = 413
    val CANT_TRANSLATE = 422
    val UNSUPPORTED_DIRECTION = 501
}
