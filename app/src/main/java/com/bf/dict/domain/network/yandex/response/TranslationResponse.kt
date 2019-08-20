package com.bf.dict.domain.network.yandex.response


class TranslationResponse : BaseResponse() {
    var lang: String? = null
    var text: List<String>? = null
}
