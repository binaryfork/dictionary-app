package com.bf.dict.ui.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BasePresenter<T : BaseView> {

    lateinit var view: T

    protected var compositeDisposable = CompositeDisposable()

    fun bindView(view: T) {
        this.view = view
    }

    fun unbindView() {
        compositeDisposable.clear()
    }

    fun <T> subscribe(observable: Single<T>, onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable {
        val disposable = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError)
        compositeDisposable.add(disposable)
        return disposable
    }

}
