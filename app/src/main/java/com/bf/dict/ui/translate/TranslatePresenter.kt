package com.bf.dict.ui.translate

import com.bf.dict.domain.exception.NoInternetConnectionException
import com.bf.dict.domain.interactor.TranslateInteractor
import com.bf.dict.domain.repository.Language
import com.bf.dict.domain.repository.Translation
import com.bf.dict.ui.base.BasePresenter
import com.bf.dict.utils.ConnectivityUtils
import io.reactivex.schedulers.Schedulers
import java.util.*

class TranslatePresenter(private val mainInteractor: TranslateInteractor, private val connectivityUtils: ConnectivityUtils) : BasePresenter<TranslateView>() {

    private val mainScreenModel: TranslateScreenModel = TranslateScreenModel()

    fun loadLanguages() {
        subscribe(mainInteractor.loadLanguages(),
                this::handleSuccessLanguages,
                this::handleError)
    }

    fun updateDirection(fromLanguage: String, toLanguage: String) {
        mainScreenModel.fromLang = fromLanguage
        mainScreenModel.toLang = toLanguage
    }

    fun translateText(text: String) {
        view.showProgress()
        subscribe(mainInteractor.translateText(text, mainScreenModel.fromLang, mainScreenModel.toLang),
                this::handleSuccessTranslate,
                this::handleError)
    }

    private fun handleSuccessLanguages(languages: ArrayList<Language>) {
        mainScreenModel.languages = languages
        view.setupSupportedLanguages(mainScreenModel)
    }

    private fun handleSuccessTranslate(translation: Translation) {
        view.setTranslatedText(translation.translated)
        view.hideProgress()
    }

    private fun handleError(throwable: Throwable) {
        if (throwable is NoInternetConnectionException) {
            view.showInternetConnectionError()
        } else {
            view.showError(throwable.localizedMessage)
        }
        view.hideProgress()
    }

    fun saveTranslation(translation: Translation) {
        if (!connectivityUtils.isOnline) {
            view.showInternetConnectionError()
            return
        }
        val disposable = mainInteractor.saveTranslation(translation)
                .subscribeOn(Schedulers.io())
                .subscribe()
        compositeDisposable.add(disposable)
    }
}
