package com.bf.dict.ui.favorites

import com.bf.dict.domain.repository.Translation
import com.bf.dict.ui.base.BaseView


interface FavoritesView : BaseView {

    fun renderView(screenModel: FavoritesScreenModel)

    fun openTranslation(translation: Translation)
}
