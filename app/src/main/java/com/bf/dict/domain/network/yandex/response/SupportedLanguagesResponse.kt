package com.bf.dict.domain.network.yandex.response


import java.util.*


class SupportedLanguagesResponse : BaseResponse() {

    var dirs: List<String> = ArrayList()

    var langs: Map<String, String> = HashMap()

}