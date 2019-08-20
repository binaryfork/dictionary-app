package com.bf.dict.ui.translate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import com.bf.dict.R
import com.bf.dict.di.MainModule
import com.bf.dict.domain.repository.Language
import com.bf.dict.domain.repository.Translation
import com.bf.dict.ui.base.BaseFragment
import com.bf.dict.ui.favorites.adapter.LanguageAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_translate.*
import javax.inject.Inject


class TranslateFragment : BaseFragment(), TranslateView {

    @Inject
    lateinit var presenter: TranslatePresenter

    private lateinit var languageAdapter: LanguageAdapter
    lateinit var viewDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        com.bf.dict.TranslateApplication.get(requireContext())
                .applicationComponent
                .mainComponent(MainModule())
                .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeDirection.setOnClickListener { this.onTranslateDirectionClick() }

        presenter.bindView(this)
        presenter.loadLanguages()

        favoriteCheckBox.setOnClickListener { presenter.saveTranslation(collectTranslation()) }
        clearButton.setOnClickListener { inputTextEt.text.clear() }

        viewDisposable = RxTextView.textChanges(inputTextEt)
                .observeOn(AndroidSchedulers.mainThread())
                .filter { if (it.isNotEmpty()) {
                    clearButton.visibility = View.VISIBLE
                    favoriteCheckBox.visibility = View.VISIBLE
                    return@filter true
                } else {
                    clearButton.visibility = View.INVISIBLE
                    favoriteCheckBox.visibility = View.INVISIBLE
                    return@filter false
                }
                }
                .subscribe({
                    presenter.translateText(it.toString())
                }, {
                    Log.e("Translate", "RxTextView " + it.message)
                })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onDestroyView() {
        viewDisposable.dispose()
        presenter.unbindView()
        super.onDestroyView()
    }

    override fun setupSupportedLanguages(screenModel: TranslateScreenModel) {
        languageAdapter = LanguageAdapter(context, android.R.layout.simple_spinner_item, screenModel.languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        fromLanguageSpinner.adapter = languageAdapter
        toLanguageSpinner.adapter = languageAdapter

        val changeLanguageListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                callTranslate()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        toLanguageSpinner.onItemSelectedListener = changeLanguageListener
        fromLanguageSpinner.onItemSelectedListener = changeLanguageListener

        selectLanguage(screenModel.fromLang, fromLanguageSpinner)
        selectLanguage(screenModel.toLang, toLanguageSpinner)
    }

    override fun setTranslatedText(text: String) {
        translatedTv.text = text
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun selectLanguage(language: String, languageSpinner: Spinner) {
        val position = languageAdapter.getPosition(language)
        if (position < 0)
            return

        val listener = languageSpinner.onItemSelectedListener
        languageSpinner.onItemSelectedListener = null
        languageSpinner.setSelection(position)
        languageSpinner.onItemSelectedListener = listener
    }

    private fun getSelectedLanguage(spinner: Spinner): String {
        val selected = spinner.selectedItem as Language
        return selected.language

    }

    private fun onTranslateDirectionClick() {
        val idFromLanguage = fromLanguageSpinner.selectedItemPosition
        val idToLanguage = toLanguageSpinner.selectedItemPosition

        fromLanguageSpinner.setSelection(idToLanguage, true)
        toLanguageSpinner.setSelection(idFromLanguage, true)
        callTranslate()
    }

    private fun callTranslate() {
        val fromLanguage = getSelectedLanguage(fromLanguageSpinner)
        val toLanguage = getSelectedLanguage(toLanguageSpinner)
        presenter.updateDirection(fromLanguage, toLanguage)
        val text = inputTextEt.text.toString()
        if (text.isNotEmpty()) {
            presenter.translateText(text)
        }
    }

    private fun collectTranslation(): Translation {
        return Translation(0,
                inputTextEt.text.toString(),
                translatedTv.text.toString(),
                getSelectedLanguage(fromLanguageSpinner),
                getSelectedLanguage(toLanguageSpinner))
    }

    fun updateTranslation(translation: Translation) {
        selectLanguage(translation.fromLanguage, fromLanguageSpinner)
        selectLanguage(translation.toLanguage, toLanguageSpinner)
        presenter.updateDirection(translation.fromLanguage, translation.toLanguage)
        inputTextEt.setText(translation.text)
        inputTextEt.setSelection(translation.text.length)
        setTranslatedText(translation.translated)
    }

}
