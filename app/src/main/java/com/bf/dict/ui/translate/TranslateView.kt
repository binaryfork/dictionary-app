package com.bf.dict.ui.translate

import com.bf.dict.ui.base.BaseView

interface TranslateView : BaseView {

    fun setupSupportedLanguages(screenModel: TranslateScreenModel)

    fun setTranslatedText(text: String)

    fun showProgress()

    fun hideProgress()
}
